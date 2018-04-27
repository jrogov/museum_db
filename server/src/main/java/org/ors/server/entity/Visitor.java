package org.ors.server.entity;

import org.springframework.data.annotation.Id;

public class Visitor
{
    @Id
    private String id;
    private String name, type;
    
    public Visitor(String name, String type)
    {
        setName(name);
        setType(type);
    }
    
    public String getName() { return this.name; }
    public String getType() { return this.type; }
    
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
}
