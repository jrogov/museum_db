package org.ors.server.dto;

import org.ors.server.dto.IDTO;
import org.ors.server.entity.ExhibitBase;
import org.ors.server.entity.ExhibitNeo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Exhibit extends ExhibitBase implements IDTO
{
    private String id;

    List<AuthorShort> authors = new ArrayList<>();

    public Exhibit() {
    }

    public Exhibit(ExhibitBase e) {
        super(e);
    }

    public Exhibit(ExhibitNeo n){
        super(n);
        setId(n.getMongoid());
        setAuthors(n.getAuthors().stream().map(AuthorShort::new).collect(Collectors.toList()));
    }

    public List<AuthorShort> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorShort> authors) {
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
