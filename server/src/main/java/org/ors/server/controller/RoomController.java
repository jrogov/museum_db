package org.ors.server.controller;

import org.ors.server.repository.RoomRepository;
import org.ors.server.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class RoomController
{
    private RoomRepository repo;
    
    @Autowired
    public RoomController(RoomRepository repo)
    {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/room", method=RequestMethod.GET)
    public Room get(@RequestParam(value="room_id") String id) throws NoSuchElementException
    {
        return repo.findById(id).get();
    }
    
    @RequestMapping(value = "/room", method=RequestMethod.POST)
    public Room add(@RequestParam("name") String name, @RequestParam("schedule") String schedule)
    {
        return new Room(name, schedule);
    }
}
