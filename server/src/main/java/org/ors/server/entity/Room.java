package org.ors.server.entity;

import org.springframework.data.annotation.Id;

public class Room
{
    @Id
    private String id;
    private String name, schedule;
    
    public Room(String name, String schedule)
    {
        this.name = name;
        this.schedule = schedule;
    }
    
    public String getName()
    {
        return name;
    }
    
    void setName(String name)
    {
        this.name = name;
    }
    
    void setSchedule(String schedule)
    {
        this.schedule = schedule;
    }
    
    public String getSchedule()
    {
        return schedule;
    }
}
