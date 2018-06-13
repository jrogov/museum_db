package org.ors.server.entity;

import org.ors.server.dto.IDTO;
import org.ors.server.dto.Interest;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.stream.Collectors;

public class Visitor extends VisitorBase implements IDTO
{
    private String id;

    List<Interest> interests;

    public Visitor() {
    }

    public Visitor(VisitorMongo m){
        super(m);
        setId(m.getId());
    }

    public Visitor(VisitorNeo n){
        super(n);
        setId(n.getMongoid());
        setInterests(n.getInterests().stream().map(Interest::new).collect(Collectors.toList()));
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
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
