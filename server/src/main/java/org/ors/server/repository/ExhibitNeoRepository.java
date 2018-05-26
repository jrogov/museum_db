package org.ors.server.repository;

import org.ors.server.dto.Exhibit;
import org.ors.server.entity.ExhibitNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitNeoRepository extends Neo4jRepository<ExhibitNeo, Long>
{
    public ExhibitNeo findByName(String name);
    public ExhibitNeo findByMongoid(String mongoid);
    public ExhibitNeo existsByMongoid(String mongoid);
//    public Exhibit findById(String id);
}
