package org.ors.server.repository;

import org.ors.server.entity.CategoryNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryNeoRepository extends Neo4jRepository<CategoryNeo, Long> {
    public CategoryNeo findByName(String name);
    public CategoryNeo existsByName(String name);
}
