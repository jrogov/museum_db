package org.ors.server.controller;

import org.ors.server.dto.DTOList;
import org.ors.server.dto.ErrorDTO;
import org.ors.server.dto.IDTO;
import org.ors.server.dto.VisitDTO;
import org.ors.server.entity.Visitor;
import org.ors.server.service.VisitorService;
import org.ors.server.util.annotations.GetJsonMapping;
import org.ors.server.util.annotations.PostJsonMapping;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorController
{
    VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostJsonMapping("/api/visitor")
    public ResponseEntity<IDTO>  addVisitor(@RequestBody Visitor visitor){
        return ResponseEntity.ok(visitorService.addOne(visitor));
    }

    @PostJsonMapping("/api/visitor/many")
    public ResponseEntity<IDTO> addVisitors(@RequestBody DTOList<Visitor> visitors){
        return ResponseEntity.ok(new DTOList<>(visitorService.addMany(visitors)));
    }

    @GetJsonMapping("/api/visitor")
    public ResponseEntity<IDTO> getAll(){
        return ResponseEntity.ok(new DTOList<>(visitorService.getAll()));
    }

    @GetJsonMapping("/api/visitor/{id}")
    public ResponseEntity<IDTO> getVisitor(@PathVariable String id){
        try{
            return ResponseEntity.ok(visitorService.getOneById(id));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
    }

    @PostJsonMapping("/api/visitor/{id}/visit")
    public ResponseEntity<IDTO> visit(@PathVariable String id, @RequestBody VisitDTO visit){
        try{
            System.out.println(id + " visits:" + visit);
            return ResponseEntity.ok(visitorService.visit(id, visit.getTickettype(), visit.getPrice(), visit.getExcursionid()));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
    }

    @GetJsonMapping("/api/visitor/{id}/interests/{name}")
    public ResponseEntity<IDTO> checkInterest(@PathVariable String id, @PathVariable String name){
        try{
            return ResponseEntity.ok(visitorService.checkInterestByName(id, name));
        } catch (DataNotFoundException e) {
            return ErrorDTO.response(e.getMessage());
        }
    }

}
