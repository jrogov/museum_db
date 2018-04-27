package org.ors.server.repository;

import org.ors.server.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String>
{
    public Room findByName(String name);
//    public Room findById(String id);
}
