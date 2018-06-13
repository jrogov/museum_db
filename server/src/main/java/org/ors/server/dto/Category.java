package org.ors.server.dto;

import org.neo4j.ogm.annotation.NodeEntity;
import org.ors.server.dto.IDTO;
import org.ors.server.entity.CategoryNeo;
import org.springframework.data.annotation.Id;

public class Category implements IDTO {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {
    }

    public Category(CategoryNeo n) {
        setName(n.getName());
    }

    @Override
    public String toString() {
        return "Category{" +
            "name='" + name + '\'' +
            '}';
    }
}
