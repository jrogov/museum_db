package org.ors.server.repository;

import org.ors.server.entity.AuthorMongo;
import org.ors.server.entity.AuthorNeo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorNeoRepository extends Neo4jRepository<AuthorNeo, Long>
{
    public AuthorNeo findByMongoid(String mongoid);
    public AuthorNeo existsByMongoid(String mongoid);
}
