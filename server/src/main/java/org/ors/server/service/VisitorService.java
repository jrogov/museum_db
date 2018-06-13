package org.ors.server.service;

import com.fasterxml.jackson.databind.node.IntNode;
import org.ors.server.dto.Category;
import org.ors.server.dto.Excursion;
import org.ors.server.dto.Interest;
import org.ors.server.dto.Visit;
import org.ors.server.entity.*;
import org.ors.server.repository.InterestNeoRepository;
import org.ors.server.repository.VisitCasRepository;
import org.ors.server.repository.VisitorMongoRepository;
import org.ors.server.repository.VisitorNeoRepository;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    private VisitorMongoRepository mongoRepo;
    private VisitorNeoRepository neoRepo;

    private InterestNeoRepository interestNeoRepo;

    private VisitCasRepository visitCasRepo;
    private ExcursionService excursionService;
    private CategoryService categoryService;

    @Autowired
    public VisitorService(VisitorMongoRepository mongoRepo,
                          VisitorNeoRepository neoRepo,
                          VisitCasRepository visitCasRepo,
                          ExcursionService excursionService,
                          CategoryService categoryService,
                          InterestNeoRepository interestNeoRepo) {
        this.mongoRepo = mongoRepo;
        this.neoRepo = neoRepo;
        this.visitCasRepo = visitCasRepo;
        this.excursionService = excursionService;
        this.categoryService = categoryService;
        this.interestNeoRepo = interestNeoRepo;
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Visitor addOne(Visitor visitor){
        VisitorMongo visitorMongo = mongoRepo.save(new VisitorMongo(visitor));
        VisitorNeo visitorNeo = neoRepo.save(new VisitorNeo(visitorMongo));
        return new Visitor(visitorNeo);
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public List<Visitor> addMany(List<Visitor> visitorList){
        ArrayList<Visitor> newVisitors = new ArrayList<>(visitorList.size());
        for( Visitor visitor : visitorList )
            newVisitors.add(addOne(visitor));
        return newVisitors;
    }

    @Transactional(readOnly = true)
    public Visitor getOneById(String id)
        throws DataNotFoundException
    {
        return new Visitor(getOneByIdNeoFull(id));
    }

    @Transactional(readOnly = true)
    public VisitorNeo getOneByIdNeoFull(String id)
        throws DataNotFoundException
    {
        VisitorNeo visitorNeo = neoRepo.findByMongoidFull(id);
        if( visitorNeo == null ) throw new DataNotFoundException("Visitor #"+id+" not found");
        return visitorNeo;
    }

    @Transactional(readOnly = true)
    public VisitorNeo getOneByIdNeo(String id)
        throws DataNotFoundException
    {
        VisitorNeo visitorNeo = neoRepo.findByMongoid(id);
        if( visitorNeo == null ) throw new DataNotFoundException("Visitor #"+id+" not found");
        return visitorNeo;
    }

    @Transactional(readOnly = true)
    public List<Visitor> getAll(){
        return mongoRepo.findAll().stream().map(Visitor::new).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public Visit visit(String visitorId, String ticketType, Double price, String excursionid)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        VisitorNeo visitorNeo = getOneByIdNeo(visitorId);
        System.out.println(visitorNeo);

        // If Excursion - update or add Visitor Interests
        if(excursionid != null ){
            ExcursionNeo excursionNeo = excursionService.getOneByIdNeo(excursionid);
            System.out.println(excursionNeo);
            CategoryNeo categoryNeo = excursionNeo.getCategory();
            System.out.println(categoryNeo);
            if(categoryNeo != null){
                Category category = new Category(categoryNeo);
//
                categoryService.getOneByName(category.getName());
                InterestNeo interestNeo = interestNeoRepo.findByVisitorIdAndCategoryName(visitorId, category.getName());

                if(interestNeo == null){
                    interestNeo = new InterestNeo(visitorNeo, categoryNeo);
                }

                interestNeo.incrementCounter();
                interestNeoRepo.save(interestNeo);
            }
        }
        VisitCas visitCas = new VisitCas(new Visitor(visitorNeo),ticketType, price, excursionid);
        return new Visit(visitCasRepo.save(visitCas));
    }

    @Transactional(readOnly = true, rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public Interest checkInterestByName(String id, String name)
        throws DataNotFoundException, DataIntegrityViolationException
    {
        Category category = categoryService.getOneByName(name);
        InterestNeo interestNeo = interestNeoRepo.findByVisitorIdAndCategoryName(id, name);
        if( interestNeo == null)
            return new Interest(0, category);
        return new Interest(interestNeo);

    }
}
