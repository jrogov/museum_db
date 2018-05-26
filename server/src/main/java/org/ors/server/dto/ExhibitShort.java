package org.ors.server.dto;

import org.ors.server.dto.IDTO;
import org.ors.server.entity.ExhibitNeo;

public class ExhibitShort implements IDTO {
    private String id;
    private String name;
    public ExhibitShort(ExhibitNeo neo){
        setId(neo.getMongoid());
        setName(neo.getName());
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
