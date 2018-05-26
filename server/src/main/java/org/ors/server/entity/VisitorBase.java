package org.ors.server.entity;

public class VisitorBase {
    protected String name;
    protected String type;

    public VisitorBase() {
    }

    public VisitorBase(VisitorBase v){
        setName(v.getName());
        setType(v.getType());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
