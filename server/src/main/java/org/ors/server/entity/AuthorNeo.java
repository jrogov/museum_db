package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity(label = "Author")
public class AuthorNeo extends AuthorBase implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    protected String mongoid;

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

    public AuthorNeo(AuthorMongo m){
        super(m);
        setMongoid(m.getId());
    }

    public AuthorNeo(AuthorNeo n){
        super(n);
        setId(n.getId());
        setMongoid(n.getMongoid());
    }
    // Neo4j IDs;

    @Relationship(type = "CONTEMPORARY", direction = Relationship.UNDIRECTED)
    private Set<AuthorNeo> contemporaries = new HashSet<>();

    @Relationship(type = "CATEGORY", direction = Relationship.OUTGOING)
    private Set<CategoryNeo> categories = new HashSet<>();

    @Relationship(type="CREATED", direction = Relationship.OUTGOING)
    private Set<ExhibitNeo> exhibits = new HashSet<>();

    public void addContemporary(AuthorNeo b){
        contemporaries.add(b);
    }

    public void addExhibit(ExhibitNeo exhibitNeo){
        exhibits.add(exhibitNeo);
    }

    public void addCategory(CategoryNeo categoryNeo){
        categories.add(categoryNeo);
    }

    public Set<AuthorNeo> getContemporaries() {
        return contemporaries;
    }

    public Set<CategoryNeo> getCategories() {
        return categories;
    }

    public Set<ExhibitNeo> getExhibits() {
        return exhibits;
    }

    public AuthorNeo() {}

//    public AuthorNeo(AuthorBase b) {
//        super(b);
//    }

    @Override
    public String toString() {
        return "AuthorNeo{" +
            "id=" + id +
            ", mongoid='" + mongoid + '\'' +
            ", name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", birthdate='" + birthdate + '\'' +
            ", deathdate='" + deathdate + '\'' +
            '}';
    }
}
