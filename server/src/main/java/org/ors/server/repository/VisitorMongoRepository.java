package org.ors.server.repository;

import org.ors.server.entity.Visitor;
import org.ors.server.entity.VisitorMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorMongoRepository extends MongoRepository<VisitorMongo, String>
{
//    public Visitor findByName(String name);
//    public Visitor findById(String id);
}
