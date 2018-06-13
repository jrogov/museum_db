package org.ors.server.repository;

import org.ors.server.entity.ExcursionNodeNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ExcursionNodeNeoRepository extends Neo4jRepository<ExcursionNodeNeo, Long> {
}
