package org.ors.server.entity;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExhibitBase {

    @NotNull
    protected String name;

    @NotNull
    protected String creationdate;

    @NotNull
    protected List<String> materials = new ArrayList<>();

    public ExhibitBase(){}

    public ExhibitBase(ExhibitBase e){
        setName(e.getName());
        setCreationdate(e.getCreationdate());
        addMaterials(e.getMaterials().toArray(new String[0]));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMaterials(String ... materials){
        this.materials.addAll(Arrays.asList(materials));
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public List<String> getMaterials() {
        return materials;
    }
}
