package org.ors.server.entity;

import org.ors.server.dto.IDTO;
import org.springframework.data.annotation.Id;

public class Visitor extends VisitorBase implements IDTO
{
    private String id;

    public Visitor() {
    }

    public Visitor(VisitorMongo m){
        super(m);
        setId(m.getId());
    }

    public Visitor(VisitorNeo n){
        super(n);
        setId(n.getMongoid());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Visitor{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}
