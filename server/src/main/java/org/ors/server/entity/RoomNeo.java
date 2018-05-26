package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.ors.server.dto.Room;

@NodeEntity(label = "Room")
public class RoomNeo extends RoomBase implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String mongoid;

    public RoomNeo(RoomNeo n){
        super(n);
        setId(n.getId());
        setMongoid(n.getMongoid());
    }

    public RoomNeo(RoomMongo m){
        super(m);
        setMongoid(m.getId());
    }

    public RoomNeo(Room b) {
        super(b);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMongoid() {
        return mongoid;
    }

    public void setMongoid(String mongoid) {
        this.mongoid = mongoid;
    }
}
