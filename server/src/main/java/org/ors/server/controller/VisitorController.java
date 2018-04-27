package org.ors.server.controller;

import org.ors.server.repository.VisitorRepository;
import org.ors.server.entity.Visitor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class VisitorController
{
    private VisitorRepository repo;
    
    public VisitorController(VisitorRepository repo)
    {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/visitor", method=RequestMethod.GET)
    public Visitor get(@RequestParam(value="visitor_id") String id) throws NoSuchElementException
    {
        return repo.findById(id).get();
    }
    
    @RequestMapping(value = "/visitor", method=RequestMethod.POST)
    public Visitor add(@RequestParam("name") String name, @RequestParam("type") String type)
    {
        return new Visitor(name, type);
    }
}
