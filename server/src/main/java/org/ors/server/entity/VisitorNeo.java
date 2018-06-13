package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity(label = "Visitor")
public class VisitorNeo extends VisitorBase implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String mongoid;

    @Relationship(type = "INTERESTED_IN", direction = Relationship.OUTGOING)
//    private Set<InterestNeo> interests = new HashSet<>();
    private Set<InterestNeo> interests = new HashSet<>();

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

    public Set<InterestNeo> getInterests() {
        return interests;
    }

    public void setInterests(Set<InterestNeo> interests) {
        this.interests = interests;
    }

    public void addInterest(InterestNeo interestNeo){
        getInterests().add(interestNeo);
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

    @Override
    public String toString() {
        return "VisitorNeo{" +
            "id=" + id +
            ", mongoid='" + mongoid + '\'' +
            ", interests=" + (
                interests == null
                    ? "null"
                    : interests.stream().map((a) -> (a == null ? "null" : a.toString())).collect(Collectors.joining(", "))
            )+
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}
