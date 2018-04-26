# CONTAINER_REGEX = "m?_"

# Used in prefixes in docker
PROJECT_NAME = DB

COMPOSE = docker-compose -p $(PROJECT_NAME)
DOCKER = docker


# Container name (M?_CONTAINER) matches subdirectory name for module-specific files
M1_IMAGE     = mongo
M1_CONTAINER = mongoc
M1_DBNAME    = museum
M1_SHELL     = mongo
M1_NET_NAME  = db_net_mongo
M1_PORT      = 27017
M1_CLEAN     = ./m1_mongo/db

M2_CONTAINER = cassandrac
M2_IMAGE     = cassandra
M2_DBNAME    = museum
M2_SHELL     = cqlsh
M2_NET_NAME  = db_net_cassandra
# Port for Native protocol clients
M2_PORT      = 9042
M2_CLEAN     = ./m2_cassandra/db

M3_CONTAINER = neo4jc
M3_IMAGE     = neo4j
M3_DBNAME    = museum
M3_CLEAN     = ./m3_neo4j/db


all:
	$(COMPOSE) up -d
	# $(DOCKER) exec $(M2_CONTAINER) $(M2_SHELL) -e "SOURCE '/docker-entrypoint-initdb.d/scheme.cql'"

stop:
	$(COMPOSE) down

m1_connect:
	$(DOCKER) exec -it $(M1_CONTAINER) $(M1_SHELL) --port $(M1_PORT)

m1_connect_external:
	$(DOCKER) run -it --net $(M1_NET_NAME) --link $(M1_CONTAINER) $(M1_IMAGE) $(M1_SHELL) $(M1_CONTAINER):$(M1_PORT)

m1_log:
	journalctl -f CONTAINER_NAME=$(M1_CONTAINER)

clean:
	$(RM) -r $(M1_CLEAN) $(M2_CLEAN) $(M3_CLEAN)

m2_connect:
	$(DOCKER) exec -it $(M2_CONTAINER) $(M2_SHELL) localhost $(M2_PORT)

m2_connect_external:
	$(DOCKER) run -it --net $(M2_NET_NAME) --link $(M2_CONTAINER) $(M2_IMAGE) $(M2_SHELL) $(M2_CONTAINER):$(M2_PORT)

m2_log:
	# Somehow
	$(DOCKER) logs $(M2_CONTAINER) | less
