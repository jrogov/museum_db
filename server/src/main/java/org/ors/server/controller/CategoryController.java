package org.ors.server.controller;

import org.ors.server.service.CategoryService;
import org.ors.server.util.annotations.GetJsonMapping;
import org.ors.server.util.annotations.PostJsonMapping;
import org.ors.server.dto.ErrorDTO;
import org.ors.server.dto.IDTO;
import org.ors.server.dto.Category;
import org.ors.server.entity.CategoryNeo;
import org.ors.server.dto.DTOList;
import org.ors.server.repository.CategoryNeoRepository;
import org.ors.server.util.exceptions.DataExistsException;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetJsonMapping("/api/category/{name}")
    public ResponseEntity<IDTO> getCategory(@PathVariable String name){
        try{
            return ResponseEntity.ok(categoryService.getOneByName(name));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }

    }

    @GetJsonMapping("/api/category")
    public ResponseEntity<IDTO> getAllCategories(){
        return ResponseEntity.ok(new DTOList<>(categoryService.getAll()));
    }

    @PostJsonMapping("/api/category")
    public ResponseEntity<IDTO> postCategory(@RequestBody Category category){
        try{
            return ResponseEntity.ok(categoryService.addOne(category));
        }
        catch (DataExistsException e){
            return ErrorDTO.response(e.getMessage());
        }
        catch(DataIntegrityViolationException e){
            return ErrorDTO.response("Wrong request format: " + category);
        }
    }

    @PostJsonMapping("/api/category/many")
    public ResponseEntity<IDTO> postCategories(@RequestBody DTOList<Category> categories) {
        try {
            return ResponseEntity.ok(new DTOList<>(categoryService.addMany(categories)));
        } catch (DataExistsException e) {
            return ErrorDTO.response(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ErrorDTO.response("Wrong request format");
        } catch (Exception e) {
            System.err.println("ERROR POST /api/category/many\n" + e);
            return ErrorDTO.response("Server error");
        }
    }
}
