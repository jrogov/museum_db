package org.ors.server.entity;

import org.ors.server.dto.Author;
import org.ors.server.dto.IDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "author")
public class AuthorMongo extends AuthorBase implements IEntity
{
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorMongo() {
    }

    public AuthorMongo(Author a){
        super(a);
        setId(a.getId());
    }
}
