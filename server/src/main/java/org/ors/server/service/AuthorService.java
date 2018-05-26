package org.ors.server.service;

import org.ors.server.dto.*;
import org.ors.server.entity.CategoryNeo;
import org.ors.server.entity.ExhibitNeo;
import org.ors.server.util.exceptions.DataExistsException;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.ors.server.entity.AuthorMongo;
import org.ors.server.entity.AuthorNeo;
import org.ors.server.repository.AuthorMongoRepository;
import org.ors.server.repository.AuthorNeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorMongoRepository mongoRepo;
    private final AuthorNeoRepository neoRepo;

    private CategoryService categoryService;
    private ExhibitService exhibitService;

    @Autowired
    public AuthorService(AuthorMongoRepository mongoRepo, AuthorNeoRepository neoRepo, CategoryService categoryService, ExhibitService exhibitService) {
        this.mongoRepo = mongoRepo;
        this.neoRepo = neoRepo;
        this.categoryService = categoryService;
        this.exhibitService = exhibitService;
    }

    private Author _addOne(Author author)
        throws DataNotFoundException
    {
        AuthorMongo authorMongo = this.mongoRepo.save(new AuthorMongo(author));
        AuthorNeo authorNeo = new AuthorNeo(authorMongo);
        for(CategoryShort c : author.getCategories()){
            authorNeo.addCategory(new CategoryNeo(categoryService.getOneByName(c.getName())));
        }
        for(ExhibitShort e : author.getExhibits()){
            authorNeo.addExhibit(exhibitService.getOneByIdNeo(e.getId()));
        }
        for(AuthorShort c : author.getContemporaries()){
            authorNeo.addContemporary(getOneByIdNeo(c.getId()));
        }
        authorNeo = this.neoRepo.save(authorNeo);
        return new Author(authorNeo);
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Author addOne(Author author)
        throws DataNotFoundException
    {
        return _addOne(author);
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public List<Author> addMany(List<Author> authors)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        List<Author> newAuthors = new ArrayList<>(authors.size());
        for( Author author : authors){
            newAuthors.add(_addOne(author));
        }
        return newAuthors;
    }
    @Transactional(readOnly = true)
    public Author getOneById(String id)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        return new Author(getOneByIdMongo(id));
    }

    @Transactional(readOnly = true)
    public AuthorMongo getOneByIdMongo(String id)
    throws DataIntegrityViolationException, DataNotFoundException
    {
        AuthorMongo authorMongo = this.mongoRepo.findById(id).get();
        if(authorMongo == null) throw new DataNotFoundException("Author #"+id+" not found");
        return authorMongo;
    }

    @Transactional(readOnly = true)
    public AuthorNeo getOneByIdNeo(String id)
    throws DataNotFoundException
    {
        AuthorNeo a = this.neoRepo.findByMongoid(id);
        if( a == null) throw new DataNotFoundException("Author #"+id+" not found");
        return a;
    }



    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public List<Author> getAll()
    {
        return new ArrayList<>(
                this.mongoRepo.findAll().stream()
                    .map(Author::new)
                    .collect(Collectors.toList())
            );
    }

    @Transactional(rollbackFor = { DataNotFoundException.class, DataIntegrityViolationException.class } )
    public Author addContemporaries(String authorId, List<String> contemporariesIds)
        throws DataNotFoundException, DataIntegrityViolationException
    {
        AuthorNeo authorNeo = neoRepo.findByMongoid(authorId);
        if(authorNeo == null) throw new DataNotFoundException("Author not found");
        for(String mongoId : contemporariesIds){
            AuthorNeo contemporary = neoRepo.findByMongoid(mongoId);
            if( contemporary == null ) throw new DataNotFoundException("Contemporary not found");
            authorNeo.addContemporary(contemporary);
            neoRepo.save(contemporary);
        }
        neoRepo.save(authorNeo);
        return new Author(authorNeo);
    }

    @Transactional(rollbackFor = {DataNotFoundException.class, DataIntegrityViolationException.class })
    public Author addExhibits(String authorId, List<String> exhibitsIds)
        throws DataNotFoundException, DataIntegrityViolationException
    {
        AuthorNeo author = getOneByIdNeo(authorId);
        if( author == null ) throw new DataNotFoundException("Author #"+authorId+" not found");
        for( String exhibitid : exhibitsIds ){
            ExhibitNeo exhibit = exhibitService.getOneByIdNeo(exhibitid);
            if( exhibit == null ) throw new DataNotFoundException("Exhibit #" + exhibitid + " not found");
            author.addExhibit(exhibit);
        }
        author = neoRepo.save(author);
        return new Author(author);
    }

    @Transactional(rollbackFor = { DataNotFoundException.class, DataIntegrityViolationException.class} )
    public Author addCategories(String authorId, List<String> categoryNames)
        throws DataNotFoundException, DataIntegrityViolationException
    {
        AuthorNeo authorNeo = getOneByIdNeo(authorId);
        if( authorNeo == null ) throw new DataNotFoundException("Author #"+authorId+" not found");
        for(String categoryName : categoryNames){
            CategoryNeo categoryNeo = categoryService.getOneByNameNeo(categoryName);
            if( categoryNeo == null) throw new DataNotFoundException("Category \""+categoryName+"\" not found");
            authorNeo.addCategory(categoryNeo);
        }
        authorNeo = neoRepo.save(authorNeo);
        return new Author(authorNeo);
    }

    @Transactional(rollbackFor = {DataNotFoundException.class, DataIntegrityViolationException.class})
    public boolean deleteOneById(String authorId)
        throws DataNotFoundException, DataIntegrityViolationException, DataExistsException
    {
        AuthorMongo authorMongo = getOneByIdMongo(authorId);
        AuthorNeo authorNeo = getOneByIdNeo(authorId);
        if( authorNeo.getExhibits().size() != 0 )
            throw new DataExistsException("Author #"+authorId+" has exhibits present - can not delete");
        mongoRepo.delete(authorMongo);
        neoRepo.delete(authorNeo);
        return true;
    }

    @Transactional(rollbackFor = {DataNotFoundException.class, DataIntegrityViolationException.class})
    public Author updateOne(Author author)
        throws DataNotFoundException, DataIntegrityViolationException
    {
        AuthorNeo authorNeo = getOneByIdNeo(author.getId());
        AuthorMongo authorMongo = getOneByIdMongo(author.getId());

        AuthorNeo newAuthorNeo = new AuthorNeo(author);
        newAuthorNeo.setId(authorNeo.getId());
        newAuthorNeo.setCategories(authorNeo.getCategories());
        newAuthorNeo.setContemporaries(authorNeo.getContemporaries());
        newAuthorNeo.setExhibits(authorNeo.getExhibits());

        AuthorMongo newAuthorMongo = new AuthorMongo(author);

        neoRepo.save(newAuthorNeo);
        mongoRepo.save(newAuthorMongo);
        return new Author(newAuthorNeo);
    }

    @Transactional(readOnly = true)
    public boolean existsById(String mongoid){
        return neoRepo.existsByMongoid(mongoid) != null;
    }

}
