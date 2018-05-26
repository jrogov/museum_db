package org.ors.server.service;

import org.ors.server.util.exceptions.DataExistsException;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.ors.server.dto.Category;
import org.ors.server.entity.CategoryNeo;
import org.ors.server.repository.CategoryNeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    CategoryNeoRepository neoRepo;

    @Autowired
    public CategoryService(CategoryNeoRepository neoRepo) {
        this.neoRepo = neoRepo;
    }

    @Transactional(readOnly = true)
    public Category getOneByName(String name) throws DataNotFoundException {
        return new Category(getOneByNameNeo(name));
    }

    @Transactional(readOnly = true)
    CategoryNeo getOneByNameNeo(String name) throws DataNotFoundException {
        CategoryNeo categoryNeo = neoRepo.findByName(name);
        if( categoryNeo == null ) throw new DataNotFoundException("Category not found");
        return categoryNeo;
    }

    @Transactional(readOnly = true)
    public List<Category> getAll(){
        return new ArrayList<>(
            StreamSupport.stream(neoRepo.findAll().spliterator(), false).map(Category::new).collect(Collectors.toList())
        );
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Category addOne(Category category) throws DataExistsException, DataIntegrityViolationException {
        if( existsByName(category.getName())) throw new DataExistsException("Category exists");
        CategoryNeo categoryNeo = neoRepo.save(new CategoryNeo(category));
        return new Category(categoryNeo);
    }

    @Transactional(rollbackFor = { DataIntegrityViolationException.class, DataExistsException.class })
    public List<Category> addMany(List<Category> categories)
        throws DataIntegrityViolationException, DataExistsException {
        List<Category> result = new ArrayList<>(categories.size());
        for( Category category : categories ){
            if( neoRepo.findByName(category.getName()) != null)
                throw new DataExistsException("Category exists: " + category.getName());
            CategoryNeo categoryNeo = neoRepo.save(new CategoryNeo(category));
            result.add(new Category(categoryNeo));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name){
        return neoRepo.existsByName(name) != null;
    }
}
