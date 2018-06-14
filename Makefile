# CONTAINER_REGEX = "m?_"

# Used in prefixes in docker
PROJECT_NAME := DB
#lowercase for network names
PROJECT_NAME_LC := db

COMPOSE_NET = docker-compose -p $(PROJECT_NAME) -f docker-compose.network.yaml
COMPOSE_CL1 = docker-compose -p $(PROJECT_NAME) -f docker-compose.cluster_1.yaml
COMPOSE_CL2 = docker-compose -p $(PROJECT_NAME) -f docker-compose.cluster_2.yaml
COMPOSE_CL3 = docker-compose -p $(PROJECT_NAME) -f docker-compose.cluster_3.yaml
COMPOSE_CLS = docker-compose -p $(PROJECT_NAME) -f docker-compose.cluster_support.yaml
COMPOSE_SRV = docker-compose -p $(PROJECT_NAME) -f docker-compose.server.yaml
#COMPOSE = docker-compose -p $(PROJECT_NAME) -f $(COMPOSE_FILE)
DOCKER = docker


# Container name (M?_CONTAINER) matches subdirectory name for module-specific files
M1_BASEDIR    = ./m1_mongo
M1_CONTAINER  = mongoc
M1_DBNAME     = museum
M1_SHELL      = mongo
M1_NET_NAME   = $(PROJECT_NAME_LC)_net_mongo
M1_GEN_FILE   = generate_mongo.js 
M11_PORT      = 27017
M12_PORT      = 27117
M13_PORT      = 27217
M1_CLEAN      = $(M1_BASEDIR)/db

M2_BASEDIR    = ./m2_cassandra
M2_CONTAINER  = cassandrac
M2_DBNAME     = museum
M2_SHELL      = cqlsh
M2_NET_NAME   = $(PROJECT_NAME_LC)_net_cassandra
M2_GEN_FILE   = generate_cassandra.cql
# Port for Native protocol clients
M21_PORT      = 9042
M22_PORT      = 9042 # 9142
M23_PORT      = 9042 # 9242
M2_CLEAN      = $(M2_BASEDIR)/db

M3_BASEDIR    = ./m3_neo4j
M3_CONTAINER  = neo4jc
M3_SHELL      = cypher-shell
M3_SCHEMA     = $(M3_BASEDIR)/schema.cql
M3_DBNAME     = museum
M3_GEN_FILE   = generate_neo4j.cql
M3_CLEAN      = $(M3_BASEDIR)/db
M3_USERNAME   = neo4j
M3_PASSWORD   = changeme
M31_PORT      = 7687
M32_PORT      = 7787
M33_PORT      = 7887
M31_PORT_WEB  = 7474
M32_PORT_WEB  = 7574
M33_PORT_WEB  = 7674
M2_NET_NAME   = $(PROJECT_NAME_LC)_net_neo4j


all: db_up init_mongo init_cassandra init_neo4j srv_up

# GENERATE

generate:
	@# Old method: through API
	@# cd generate && ./generate.py
	cd generate &&                                                                                                                    \
	./generate_query.py &&                                                                                                            \
	cat $(M1_GEN_FILE) | $(DOCKER) exec -i $(M1_CONTAINER)1 $(M1_SHELL) --port $(M11_PORT) $(M1_DBNAME) && echo 'Mongo Gen Done' &&                            \
	cat $(M2_GEN_FILE) | $(DOCKER) exec -i $(M2_CONTAINER)1 $(M2_SHELL) localhost $(M21_PORT) -k $(M2_DBNAME) && echo 'Cas Gen Done' &&                      \
	cat $(M3_GEN_FILE) | $(DOCKER) exec -i $(M3_CONTAINER)1 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M31_PORT) && echo 'Neo Gen Done'
# UP

db_up: net_up c1_up c2_up c3_up cs_up

net_up:
	$(COMPOSE_NET) up -d

c1_up:
	$(COMPOSE_CL1) up -d
	
c2_up:
	$(COMPOSE_CL2) up -d
	
c3_up:
	$(COMPOSE_CL3) up -d
	
cs_up:
	$(COMPOSE_CLS) up -d

srv_up:
	#$(COMPOSE_SRV) up -d

# INIT

init_mongo:
	sleep 10 && $(DOCKER) exec -it $(M1_CONTAINER)1 $(M1_SHELL) --port $(M11_PORT) $(M1_DBNAME) /etc/configure-rs.js

init_cassandra:
	cat ./m2_cassandra/scheme.cql | $(DOCKER) exec -i $(M2_CONTAINER)1 $(M2_SHELL) localhost $(M21_PORT) 

init_neo4j:
	# no init for neo4j

# STOP
	
stop: c1_down c2_down c3_down cs_down srv_down
	$(COMPOSE_NET) down

c1_down:
	$(COMPOSE_CL1) down

c2_down: 
	$(COMPOSE_CL2) down

c3_down:
	$(COMPOSE_CL3) down

cs_down:
	$(COMPOSE_CLS) down

srv_down:
	# $(COMPOSE_SRV) down

m11_log:
	journalctl -f CONTAINER_NAME=$(M1_CONTAINER)1

m12_log:
	journalctl -f CONTAINER_NAME=$(M1_CONTAINER)2

m13_log:
	journalctl -f CONTAINER_NAME=$(M1_CONTAINER)3

c1_stop:
	$(COMPOSE_CL1) stop

c2_stop:
	$(COMPOSE_CL2) stop

c3_stop:
	$(COMPOSE_CL3) stop

# CLEAN

clean: m1_clean m2_clean m3_clean

m1_clean:
	# Only one of them is primary: others fail trying to delete
	$(DOCKER) exec -it $(M1_CONTAINER)1 $(M1_SHELL) --port $(M11_PORT) $(M1_DBNAME) --eval \
	    'db.getCollectionNames().map( (a) => db[a].deleteMany({}))' || :
	$(DOCKER) exec -it $(M1_CONTAINER)2 $(M1_SHELL) --port $(M12_PORT) $(M1_DBNAME) --eval \
	    'db.getCollectionNames().map( (a) => db[a].deleteMany({}))' || :
	$(DOCKER) exec -it $(M1_CONTAINER)3 $(M1_SHELL) --port $(M13_PORT) $(M1_DBNAME) --eval \
	    'db.getCollectionNames().map( (a) => db[a].deleteMany({}))' || :

m2_clean:
	$(DOCKER) exec -i $(M2_CONTAINER)1 $(M2_SHELL) localhost $(M21_PORT) -k $(M2_DBNAME) -e 'TRUNCATE relocation ; TRUNCATE visit ;' || : 
	$(DOCKER) exec -i $(M2_CONTAINER)2 $(M2_SHELL) localhost $(M22_PORT) -k $(M2_DBNAME) -e 'TRUNCATE relocation ; TRUNCATE visit ;' || :
	$(DOCKER) exec -i $(M2_CONTAINER)3 $(M2_SHELL) localhost $(M23_PORT) -k $(M2_DBNAME) -e 'TRUNCATE relocation ; TRUNCATE visit ;' || :

m3_clean:
	$(DOCKER) exec -it $(M3_CONTAINER)1 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M31_PORT) 'match(n) detach delete n;' || :
	$(DOCKER) exec -it $(M3_CONTAINER)2 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M32_PORT) 'match(n) detach delete n;' || :
	$(DOCKER) exec -it $(M3_CONTAINER)3 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M33_PORT) 'match(n) detach delete n;' || :


unsafe_clean:
	$(RM) -r \
	    $(M1_BASEDIR)/arb1 $(M1_BASEDIR)/arb2 \
	    $(M1_CLEAN)1 $(M2_CLEAN)1 $(M3_CLEAN)1 \
	    $(M1_CLEAN)2 $(M2_CLEAN)2 $(M3_CLEAN)2 \
	    $(M1_CLEAN)3 $(M2_CLEAN)3 $(M3_CLEAN)3 \

# SHELL and GUI

m11_shell:
	$(DOCKER) exec -it $(M1_CONTAINER)1 $(M1_SHELL) --port $(M11_PORT) $(M1_DBNAME)

m12_shell:
	$(DOCKER) exec -it $(M1_CONTAINER)2 $(M1_SHELL) --port $(M12_PORT) $(M1_DBNAME)

m13_shell:
	$(DOCKER) exec -it $(M1_CONTAINER)3 $(M1_SHELL) --port $(M13_PORT) $(M1_DBNAME)

m21_shell:
	$(DOCKER) exec -it $(M2_CONTAINER)1 $(M2_SHELL) localhost $(M21_PORT) -k $(M2_DBNAME)

m22_shell:
	$(DOCKER) exec -it $(M2_CONTAINER)2 $(M2_SHELL) localhost $(M22_PORT) -k $(M2_DBNAME)

m23_shell:
	$(DOCKER) exec -it $(M2_CONTAINER)3 $(M2_SHELL) localhost $(M23_PORT) -k $(M2_DBNAME)

m31_shell:
	$(DOCKER) exec -it $(M3_CONTAINER)1 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M31_PORT)

m32_shell:
	$(DOCKER) exec -it $(M3_CONTAINER)2 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M32_PORT)

m33_shell:
	$(DOCKER) exec -it $(M3_CONTAINER)3 $(M3_SHELL) -u $(M3_USERNAME) -p $(M3_PASSWORD) -a localhost:$(M33_PORT)

m3_browser:
	xdg-open http://localhost:$(M31_PORT_WEB)

# LOG

m21_log:
	# Somehow
	# $(DOCKER) logs $(M2_CONTAINER) | less
	journalctl -f CONTAINER_NAME=$(M2_CONTAINER)1

m22_log:
	journalctl -f CONTAINER_NAME=$(M2_CONTAINER)2
m23_log:
	journalctl -f CONTAINER_NAME=$(M2_CONTAINER)3

m31_log:
	journalctl -f CONTAINER_NAME=$(M3_CONTAINER)1

m32_log:
	journalctl -f CONTAINER_NAME=$(M3_CONTAINER)2

m33_log:
	journalctl -f CONTAINER_NAME=$(M3_CONTAINER)3

# UTILS

backup: stop
	rm -rf .backup
	mkdir -p .backup
	cp -r $(M1_BASEDIR) $(M2_BASEDIR) $(M3_BASEDIR) .backup

restore: stop
	rm -r $(M1_BASEDIR) $(M2_BASEDIR) $(M3_BASEDIR)
	cp -r .backup/$(M1_BASEDIR) .backup/$(M2_BASEDIR) .backup/$(M3_BASEDIR) . 

check_ports:
	#dont forget sudo
	lsof -i | grep docker

.PHONY: all up init_cassandra init_neo4j generate \
	m1_shell m2_shell m3_shell
