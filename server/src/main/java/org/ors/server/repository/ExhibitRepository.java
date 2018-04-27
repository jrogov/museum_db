package org.ors.server.repository;

import org.ors.server.entity.Exhibit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepository extends MongoRepository<Exhibit, String>
{
    public Exhibit findByName(String name);
//    public Exhibit findById(String id);
}
