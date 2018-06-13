package org.ors.server.entity;

import org.neo4j.ogm.annotation.*;
import org.ors.server.dto.Interest;

@RelationshipEntity(type = "INTERESTED_IN")
public class InterestNeo implements IEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Integer counter = 0;

    @StartNode
    private VisitorNeo visitor;

    @EndNode
    private CategoryNeo category;

    public InterestNeo() {
    }

    public InterestNeo(Interest interest){
        setCounter(interest.getCounter());
        setCategory(new CategoryNeo(interest.getCategory()));
    }

    public InterestNeo(VisitorNeo visitor, CategoryNeo category) {
        this.counter = 0;
        this.visitor = visitor;
        this.category = category;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public void incrementCounter(){ this.counter++; }

    public VisitorNeo getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorNeo visitor) {
        this.visitor = visitor;
    }

    public CategoryNeo getCategory() {
        return category;
    }

    public void setCategory(CategoryNeo category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "InterestNeo{" +
            "counter=" + counter +
            ", visitor=" + visitor +
            ", category=" + category +
            '}';
    }
}
