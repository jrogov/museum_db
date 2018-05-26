package org.ors.server.controller;

import org.ors.server.service.ExhibitService;
import org.ors.server.util.annotations.GetJsonMapping;
import org.ors.server.util.annotations.PostJsonMapping;
import org.ors.server.dto.DTOList;
import org.ors.server.dto.ErrorDTO;
import org.ors.server.dto.IDTO;
import org.ors.server.entity.ExhibitMongo;
import org.ors.server.entity.ExhibitNeo;
import org.ors.server.dto.Exhibit;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@RestController
public class ExhibitController
{
    private ExhibitService exhibitService;

    @Autowired
    public ExhibitController(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }
    
    @GetJsonMapping("/api/exhibit")
    public ResponseEntity<IDTO> getAllExhibits(){
        return ResponseEntity.ok(new DTOList<>(exhibitService.getAll()));
    }

    @GetJsonMapping("/api/exhibit/{id}")
    public ResponseEntity<IDTO> getExhibit(@PathVariable String id){
        try {
            return ResponseEntity.ok(exhibitService.getOneById(id));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
    }

    @PostJsonMapping("/api/exhibit")
    public ResponseEntity<IDTO> postExhibit(@RequestBody Exhibit exhibit){
        try{
            return ResponseEntity.ok(exhibitService.addOne(exhibit));
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format: "+exhibit);
        }
    }

    @PostJsonMapping("/api/exhibit/many")
    public ResponseEntity<IDTO> postExhibits(@RequestBody DTOList<Exhibit> exhibits){
        try{
            return ResponseEntity.ok(new DTOList<>(exhibitService.addMany(exhibits)));
        }
        catch (DataIntegrityViolationException e){
            return ErrorDTO.response(e.getMessage());
        }
    }
}
