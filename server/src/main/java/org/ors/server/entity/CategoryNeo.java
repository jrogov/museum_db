package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.ors.server.dto.Category;

@NodeEntity(label = "Category")
public class CategoryNeo implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryNeo() {
    }

    public CategoryNeo(Category c) {
        setName(c.getName());
    }

    //    @Relationship(type="CATEGORY", direction = Relationship.INCOMING)
//    private List<ExhibitNeo> exhibits = new ArrayList<>();

}
