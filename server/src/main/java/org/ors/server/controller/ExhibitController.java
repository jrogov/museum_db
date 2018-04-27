package org.ors.server.controller;

import org.ors.server.repository.ExhibitRepository;
import org.ors.server.entity.Exhibit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class ExhibitController
{
    private ExhibitRepository repo;
    
    @Autowired
    public ExhibitController(ExhibitRepository repo)
    {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/exhibit", method = RequestMethod.GET)
    public Exhibit get(@RequestParam("name") String name)
    {
        return repo.findByName(name);
    }
}
