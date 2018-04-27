package org.ors.server.controller;

import org.ors.server.repository.AuthorRepository;
import org.ors.server.entity.Author;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@RestController
public class AuthorController
{
    private AuthorRepository repo;
    
    @Autowired
    public AuthorController(AuthorRepository repo)
    {
        this.repo = repo;
    }
    
    @RequestMapping(value="/author", method=RequestMethod.GET)
    public Author get(@RequestParam("id") String id) throws NoSuchElementException
    {
        return this.repo.findById(id).get();
    }
    
}
