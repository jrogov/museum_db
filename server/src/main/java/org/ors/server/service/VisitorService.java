package org.ors.server.service;

import org.ors.server.entity.Visitor;
import org.ors.server.entity.VisitorMongo;
import org.ors.server.entity.VisitorNeo;
import org.ors.server.repository.VisitorMongoRepository;
import org.ors.server.repository.VisitorNeoRepository;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    private VisitorMongoRepository mongoRepo;
    private VisitorNeoRepository neoRepo;

    @Autowired
    public VisitorService(VisitorMongoRepository mongoRepo, VisitorNeoRepository neoRepo) {
        this.mongoRepo = mongoRepo;
        this.neoRepo = neoRepo;
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
        VisitorNeo visitorNeo = neoRepo.findByMongoid(id);
        if( visitorNeo == null ) throw new DataNotFoundException("Visitor #"+id+" not found");
        return new Visitor(visitorNeo);
    }

    @Transactional(readOnly = true)
    public List<Visitor> getAll(){
        return mongoRepo.findAll().stream().map(Visitor::new).collect(Collectors.toList());
    }
}
