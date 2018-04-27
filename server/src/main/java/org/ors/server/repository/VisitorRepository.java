package org.ors.server.repository;

import org.ors.server.entity.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends MongoRepository<Visitor, String>
{
    public Visitor findByName(String name);
//    public Visitor findById(String id);
}
