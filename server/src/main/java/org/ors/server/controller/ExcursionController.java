package org.ors.server.controller;

import org.ors.server.dto.DTOList;
import org.ors.server.dto.ErrorDTO;
import org.ors.server.dto.Excursion;
import org.ors.server.dto.IDTO;
import org.ors.server.service.ExcursionService;
import org.ors.server.util.annotations.GetJsonMapping;
import org.ors.server.util.annotations.PostJsonMapping;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcursionController {

    ExcursionService excursionService;

    @Autowired
    public ExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @PostJsonMapping("/api/excursion")
    public ResponseEntity<IDTO> addExcursion(@RequestBody Excursion excursion){
        try{
            System.out.println(excursion);
            return ResponseEntity.ok(excursionService.addOne(excursion));
        } catch (DataNotFoundException e) {
            return ErrorDTO.response(e.getMessage());
        }
    }

    @PostJsonMapping("/api/excursion/many")
    public ResponseEntity<IDTO> addExcursions(@RequestBody DTOList<Excursion> excursions){
        try{
            return ResponseEntity.ok(new DTOList<>(excursionService.addMany(excursions)));
        } catch (DataNotFoundException e) {
            return ErrorDTO.response(e.getMessage());
        }
    }

    @GetJsonMapping("/api/excursion")
    public ResponseEntity<IDTO> getExcursion(){
        return ResponseEntity.ok(new DTOList<>(excursionService.getAll()));
    }

    @GetJsonMapping("/api/excursion/{id}")
    public ResponseEntity<IDTO> getExcursions(@PathVariable String id){
        try{
            return ResponseEntity.ok(excursionService.getOneById(id));
        } catch (DataNotFoundException e) {
            return ErrorDTO.response(e.getMessage());
        }
    }
}
