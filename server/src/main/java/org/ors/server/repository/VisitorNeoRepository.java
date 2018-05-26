package org.ors.server.repository;

import org.ors.server.entity.VisitorNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VisitorNeoRepository extends Neo4jRepository<VisitorNeo, Long> {
    public VisitorNeo findByMongoid(String id);
}
