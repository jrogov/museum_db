CONTAINER_REGEX = "m?_"

M1_CONTAINER = m1_mongo
M1_IMAGE     = mongo
M2_DBNAME    = museum

M2_CONTAINER = m2_cassandra
M2_IMAGE     = cassandra
M2_DBNAME    = museum

M3_CONTAINER = m3_neo4j
M3_IMAGE     = neo4j
M3_DBNAME    = museum


start: all_init all_reload

all_init: m1_init m2_init m3_init

m1_init:
	docker container inspect $(M1_CONTAINER) >/dev/null 2>/dev/null || docker container create --name $(M1_CONTAINER) $(M1_IMAGE)

m2_init:
	docker container inspect $(M2_CONTAINER) >/dev/null 2>/dev/null || docker container create --name $(M2_CONTAINER) $(M2_IMAGE)

m3_init:
	docker container inspect $(M3_CONTAINER) >/dev/null 2>/dev/null || docker container create --name $(M3_CONTAINER) $(M3_IMAGE)


status:
	docker container ls --filter "name=$(CONTAINER_REGEX)"

# RELOAD/START RULES	

all_reload: m1_reload m2_reload m3_reload

m1_reload:
	docker container restart $(M1_CONTAINER)

m2_reload:
	docker container restart $(M2_CONTAINER)

m3_reload:
	docker container restart $(M3_CONTAINER)



# STOP RULES

all_stop: m1_stop m2_stop m3_stop

m1_stop:
	docker container stop $(M1_CONTAINER)

m2_stop:
	docker container stop $(M2_CONTAINER)

m3_stop:
	docker container stop $(M3_CONTAINER)



# CONNECT WITH CLI CLIENT 

m1_connect:
	docker run -it --link $(M1_CONTAINER):mongo --rm mongo sh -c 'exec mongo "$MONGO_PORT_27017_TCP_ADDR:$MONGO_PORT_27017_TCP_PORT/"'"$(M1_DBNAME)"

m2_connect:
	docker run -it --link $(M2_CONTAINER):cassandra --rm cassandra sh -c 'exec cqlsh "$CASSANDRA_PORT_9042_TCP_ADDR"'

m3_connect:
	docker run -ti $(M3_CONTAINER) /var/lib/neo4j/bin/neo4j-shell
