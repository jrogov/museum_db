package org.ors.server.repository;

import org.ors.server.entity.InterestNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface InterestNeoRepository extends Neo4jRepository<InterestNeo, Long> {
    @Query("MATCH (n:Visitor) -[i:INTERESTED_IN]-> (c:Category) WHERE n.mongoid = {0} AND c.name = {1} return n,i,c")
    public InterestNeo findByVisitorIdAndCategoryName(String mongoid, String name);
}
