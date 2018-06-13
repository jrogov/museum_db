package org.ors.server.repository;

import org.ors.server.dto.Excursion;
import org.ors.server.entity.ExcursionNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ExcursionNeoRepository extends Neo4jRepository<ExcursionNeo, Long> {

    @Query("MATCH (c:Category)<-[cr:CATEGORY]-(e:Excursion)-[nr:NODE]->(r:Room) WHERE ID(e) = {0} RETURN c,cr,e,nr,r")
    public ExcursionNeo findByIdFull(Long id);

//    @Query("MATCH (e:Excursion)-[n:NODE]->(r:Room) RETURN e,n,r")
    @Query("MATCH (c:Category)<-[cr:CATEGORY]-(e:Excursion)-[nr:NODE]->(r:Room) WHERE ID(e) = {0} RETURN c,cr,e,nr,r")
    public List<ExcursionNeo> findAllFull();
}
