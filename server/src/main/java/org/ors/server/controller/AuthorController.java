package org.ors.server.controller;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.ors.server.dto.*;
import org.ors.server.entity.AuthorNeo;
import org.ors.server.util.annotations.DeleteJsonMapping;
import org.ors.server.util.annotations.PutJsonMapping;
import org.ors.server.util.exceptions.DataExistsException;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.ors.server.util.annotations.GetJsonMapping;
import org.ors.server.util.annotations.PostJsonMapping;
import org.ors.server.service.AuthorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/*
* Use as a reference implementation of REST API methods for this project
* */

@RestController
public class AuthorController
{
    private AuthorService authorService;

    @Autowired
    private AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /*
    * Get all authors
    * */
    @GetJsonMapping("/api/author")
    public ResponseEntity<IDTO> getAllAuthors(){
        return ResponseEntity.ok(new DTOList<>(authorService.getAll()));
    }

    @GetJsonMapping("/api/author/{id}")
    public ResponseEntity<IDTO> getAuthor(@PathVariable String id)
    {
        try {
            return ResponseEntity.ok(authorService.getOneById(id));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (NoSuchElementException e){
            return ErrorDTO.response("Author not found");
        }
    }

    @PostJsonMapping("/api/author")
    public ResponseEntity<IDTO> postAuthor(@RequestBody Author author) {
        try{
            return ResponseEntity.ok(authorService.addOne(author));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format: "+author);
        }
    }

    @PostJsonMapping("/api/author/many")
    public ResponseEntity<IDTO> postAuthors(@RequestBody DTOList<Author> authors) {
        try {
            return ResponseEntity.ok(
                new DTOList<>(authorService.addMany(authors))
            );
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format");
        }
    }

    @PostJsonMapping("/api/author/{id}/contemporaries")
    public ResponseEntity<IDTO> postContemporaries(@PathVariable String id, @RequestBody DTOList<String> contemporariesIds){

        try{
            return ResponseEntity.ok(
                authorService.addContemporaries(id, contemporariesIds)
            );
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format");
        }
    }

    @PostJsonMapping("/api/author/{id}/works")
    public ResponseEntity<IDTO> postWorks(@PathVariable String id, @RequestBody DTOList<String> exhibitIds){
        try{
            return ResponseEntity.ok(authorService.addExhibits(id, exhibitIds));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format");
        }
    }

    @PostJsonMapping("/api/author/{id}/categories")
    public ResponseEntity<IDTO> postCategories(@PathVariable String id, @RequestBody DTOList<String> categoryNames){
        try{
            return ResponseEntity.ok(authorService.addCategories(id, categoryNames));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format");
        }
    }

    @DeleteJsonMapping("/api/author/{id}")
    public ResponseEntity<IDTO> deleteAuthor(@PathVariable String id){
        try {
            if( authorService.deleteOneById(id) )
                return MessageDTO.response("Author #"+id+" deleted");
            return ErrorDTO.response("Deletion of #"+id+" failed");
        }
        catch (DataExistsException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
    }

    @PutJsonMapping("/api/author/{id}")
    public ResponseEntity<IDTO> updateAuthor(@PathVariable String id, @RequestBody Author author){
        try {
            author.setId(id);
            return ResponseEntity.ok(authorService.updateOne(author));
        } catch (DataNotFoundException e) {
            return ErrorDTO.response(e.getMessage());
        }
    }
}
