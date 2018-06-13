package org.ors.server.repository;

import org.ors.server.entity.CategoryNeo;
import org.ors.server.entity.InterestNeo;
import org.ors.server.entity.VisitorNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VisitorNeoRepository extends Neo4jRepository<VisitorNeo, Long> {

    public VisitorNeo findByMongoid(String id);

    @Query("MATCH (n:Visitor) WHERE n.mongoid = {0}  OPTIONAL MATCH (n)-[i:INTERESTED_IN]-> (c:Category) return n,i,c ")
//    @Query("MATCH (n:Visitor) WHERE n.mongoid = {0} return n;")
    public VisitorNeo findByMongoidFull(String id);
}
