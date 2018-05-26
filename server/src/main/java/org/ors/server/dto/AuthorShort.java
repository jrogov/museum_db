package org.ors.server.dto;

import org.ors.server.entity.AuthorNeo;

public class AuthorShort implements IDTO {
    private String id;
    private String name;

    public AuthorShort(AuthorNeo n){
        setId(n.getMongoid());
        setName(n.getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
