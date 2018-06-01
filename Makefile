# CONTAINER_REGEX = "m?_"

# Used in prefixes in docker
PROJECT_NAME := DB
#lowercase for network names
PROJECT_NAME_LC := db

COMPOSE_FILE = docker-compose.cluster.yaml
COMPOSE = docker-compose -p $(PROJECT_NAME) -f $(COMPOSE_FILE)
DOCKER = docker


# Container name (M?_CONTAINER) matches subdirectory name for module-specific files
M1_BASEDIR 	 = ./m1_mongo
M1_IMAGE     = mongo
M1_CONTAINER = mongoc
M1_DBNAME    = museum
M1_SHELL     = mongo
M1_NET_NAME  = $(PROJECT_NAME_LC)_net_mongo
M1_PORT      = 27017
M1_CLEAN     = $(M1_BASEDIR)/db

M2_BASEDIR   = ./m2_cassandra
M2_CONTAINER = cassandrac
M2_IMAGE     = cassandra
M2_DBNAME    = museum
M2_SHELL     = cqlsh
M2_NET_NAME  = $(PROJECT_NAME_LC)_net_cassandra
# Port for Native protocol clients
M2_PORT      = 9042
M2_CLEAN     = $(M2_BASEDIR)/db

M3_BASEDIR   = ./m3_neo4j
M3_CONTAINER = neo4jc
M3_SHELL	 = cypher-shell
M3_SCHEMA    = $(M3_BASEDIR)/schema.cql
M3_IMAGE     = neo4j
M3_DBNAME    = museum
M3_CLEAN     = $(M3_BASEDIR)/db
M3_PORT_WEB  = 7474
M3_PORT      = 1337
M3_NET_NAME  = $(PROJECT_NAME_LC)_net_neo4j


all: up init_mongo init_cassandra init_neo4j

generate:
	# nodejs generate/generate.js
	cd generate && ./generate.py

up:
	$(COMPOSE) up -d

init_mongo:
	$(DOCKER) exec -it $(M1_CONTAINER)1 $(M1_SHELL) --port $(M1_PORT) $(M1_DBNAME) /etc/configure-rs.js

# Not used as was implemented with entrypoint shell script
# Inited at startup
init_cassandra:
	# $(DOCKER) exec $(M2_CONTAINER) $(M2_SHELL) -e "SOURCE '/docker-entrypoint-initdb.d/scheme.cql'"

# NO SCHEMAS FOR NEO4J
# NOSQL 4 NOLIFE
init_neo4j:
	# $(DOCKER) exec -it $(M3_CONTAINER) $(M3_SHELL) < $(M3_SCHEMA)

stop:
	$(COMPOSE) down

m1_restart:
	$(COMPOSE) restart module1

m1_shell:
	$(DOCKER) exec -it $(M1_CONTAINER) $(M1_SHELL) --port $(M1_PORT) $(M1_DBNAME)

m1_shell_external:
	$(DOCKER) run -it --net $(M1_NET_NAME) --link $(M1_CONTAINER) $(M1_IMAGE) $(M1_SHELL) $(M1_CONTAINER):$(M1_PORT) $(M1_DBNAME)

m1_log:
	journalctl -f CONTAINER_NAME=$(M1_CONTAINER)

m1_clean:
	$(DOCKER) exec -it $(M1_CONTAINER)1 $(M1_SHELL) --port 27017 $(M1_DBNAME) --eval \
	    'db.getCollectionNames().map( (a) => db[a].deleteMany({}))' || :
	$(DOCKER) exec -it $(M1_CONTAINER)2 $(M1_SHELL) --port 27027 $(M1_DBNAME) --eval \
	    'db.getCollectionNames().map( (a) => db[a].deleteMany({}))' || :
	$(DOCKER) exec -it $(M1_CONTAINER)3 $(M1_SHELL) --port 27037  $(M1_DBNAME) --eval \
	    'db.getCollectionNames().map( (a) => db[a].deleteMany({}))' || :

m1_1_stop:
	$(COMPOSE) stop module1_1

m1_2_stop:
	$(COMPOSE) stop module1_2

m1_3_stop:
	$(COMPOSE) stop module1_3

clean: m1_clean m2_clean m3_clean

unsafe_clean:
	$(RM) -r $(M1_CLEAN) $(M2_CLEAN) $(M3_CLEAN)

m2_restart:
	$(COMPOSE) restart module2

m2_shell:
	$(DOCKER) exec -it $(M2_CONTAINER) $(M2_SHELL) localhost $(M2_PORT) -k $(M2_DBNAME)

m2_clean:
	# probably TODO: not needed for now

m2_shell_external:
	$(DOCKER) run -it --net $(M2_NET_NAME) --link $(M2_CONTAINER) $(M2_IMAGE) $(M2_SHELL) $(M2_CONTAINER):$(M2_PORT) -k $(M2_DBNAME)

m2_log:
	# Somehow
	$(DOCKER) logs $(M2_CONTAINER) | less

m3_restart:
	$(COMPOSE) restart module3

m3_browser:
	xdg-open http://localhost:$(M3_PORT_WEB)

m3_shell:
	$(DOCKER) exec -it $(M3_CONTAINER) $(M3_SHELL)

m3_clean:
	$(DOCKER) exec -it $(M3_CONTAINER) $(M3_SHELL) \
	'match(n) detach delete n;'

backup: stop
	rm -rf .backup
	mkdir -p .backup
	cp -r $(M1_BASEDIR) $(M2_BASEDIR) $(M3_BASEDIR) .backup

restore: stop
	rm -r $(M1_BASEDIR) $(M2_BASEDIR) $(M3_BASEDIR)
	cp -r .backup/$(M1_BASEDIR) .backup/$(M2_BASEDIR) .backup/$(M3_BASEDIR) . 




#dont forget sudo
check_ports:
	lsof -i | grep docker

.PHONY: all up init_cassandra init_neo4j generate \
	m1_shell m2_shell m3_shell
