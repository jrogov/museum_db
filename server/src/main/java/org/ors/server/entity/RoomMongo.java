package org.ors.server.entity;

import org.ors.server.dto.Room;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "room")
public class RoomMongo extends RoomBase implements IEntity {

    @Id
    private String id;

    public RoomMongo(Room r){
        super(r);
    }

    public RoomMongo(RoomMongo m) {
        super(m);
        setId(m.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
