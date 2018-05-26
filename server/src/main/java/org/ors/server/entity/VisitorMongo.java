package org.ors.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "visitor")
public class VisitorMongo extends VisitorBase implements IEntity {

    @Id
    private String id;

    public VisitorMongo(Visitor v){
        super(v);
        setId(v.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
