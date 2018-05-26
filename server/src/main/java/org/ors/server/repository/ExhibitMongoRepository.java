package org.ors.server.repository;

import org.ors.server.dto.Exhibit;
import org.ors.server.entity.ExhibitMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitMongoRepository extends MongoRepository<ExhibitMongo, String>
{
    public Exhibit findByName(String name);
//    public Exhibit findById(String id);
}
