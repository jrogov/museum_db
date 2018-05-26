package org.ors.server.repository;

import org.ors.server.entity.RoomNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RoomNeoRepository extends Neo4jRepository<RoomNeo,Long> {
    public RoomNeo findByMongoid(String id);
}
