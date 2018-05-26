package org.ors.server.repository;

import org.ors.server.entity.AuthorMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, String>
{
}
