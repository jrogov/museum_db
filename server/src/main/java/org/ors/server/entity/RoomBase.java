package org.ors.server.entity;

public class RoomBase
{
    private String name, schedule;

    public RoomBase() {
    }

    protected RoomBase(RoomBase b) {
        setName(b.getName());
        setSchedule(b.getSchedule());
    }

    public RoomBase(String name, String schedule)
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
