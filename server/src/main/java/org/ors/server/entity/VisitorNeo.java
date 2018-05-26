package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Visitor")
public class VisitorNeo extends VisitorBase implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String mongoid;

    public VisitorNeo() {
    }

    public VisitorNeo(VisitorMongo m){
        super(m);
        setMongoid(m.getId());
    }

    public VisitorNeo(Visitor v){
        super(v);
        setMongoid(v.getId());
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
