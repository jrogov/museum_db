package org.ors.server.repository;

import org.ors.server.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String>
{
    public Author findByName(String name);
//    public Author findById(String id);
//    public Optional<Author> findById(String id);
}
