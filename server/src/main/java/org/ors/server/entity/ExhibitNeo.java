package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.ors.server.dto.Exhibit;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Exhibit")
public class ExhibitNeo extends ExhibitBase implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    public String getMongoid() {
        return mongoid;
    }

    public void setMongoid(String mongoId) {
        this.mongoid = mongoId;
    }

    private String mongoid;

//    @Relationship(type = "CATEGORY", direction = Relationship.OUTGOING)
//    List<CategoryNeo> categories = new ArrayList<>();

    public Set<AuthorNeo> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorNeo> authors) {
        this.authors = authors;
    }

    @Relationship(type="CREATED", direction = Relationship.INCOMING)
    private Set<AuthorNeo> authors = new HashSet<>();

    public ExhibitNeo(Exhibit e){
        super(e);
        setMongoid(e.getId());
        addMaterials(e.getMaterials().toArray(new String[0]));
//        setAuthors(e.getAuthors().stream().map(AuthorNeo::new));
    }

    public ExhibitNeo(ExhibitMongo m){
        super(m);
        setMongoid(m.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExhibitNeo() {
    }


    //    public void addCategories(CategoryNeo ... categories){
//        this.categories.addAll(Arrays.asList(categories));
//    }

//    public List<CategoryNeo> getCategories() {
//        return categories;
//    }

}
