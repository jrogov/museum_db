package org.ors.server.dto;

import org.ors.server.entity.RoomBase;
import org.ors.server.entity.RoomMongo;
import org.ors.server.entity.RoomNeo;

public class Room extends RoomBase implements IDTO {

    private String id;

    public Room() {
    }

    public Room(RoomMongo m){
        super(m);
        setId(m.getId());
    }

    public Room(RoomNeo n) {
        super(n);
        setId(n.getMongoid());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
