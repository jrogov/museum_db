package org.ors.server.entity;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "NODE")
public class ExcursionNodeNeo implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    // Index in "array" of all rooms in excursion, i.e. it's index number
    private Integer index;

    @StartNode
    private ExcursionNeo excursion;

    @EndNode
    private RoomNeo room;

    public ExcursionNodeNeo() {
    }

    public ExcursionNodeNeo(Integer index, ExcursionNeo excursion, RoomNeo room) {
        this.index = index;
        this.excursion = excursion;
        this.room = room;
    }

    public ExcursionNeo getExcursion() {
        return excursion;
    }

    public void setExcursion(ExcursionNeo excursion) {
        this.excursion = excursion;
    }

    public Long getId() {
        return id;
    }

    public Integer getIndex() {
        return index;
    }

    public RoomNeo getRoom() {
        return room;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setRoom(RoomNeo room) {
        this.room = room;
    }
}
