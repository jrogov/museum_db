package org.ors.server.service;

import com.sun.org.apache.xerces.internal.dom.DeferredAttrNSImpl;
import org.ors.server.dto.Excursion;
import org.ors.server.dto.Room;
import org.ors.server.entity.CategoryNeo;
import org.ors.server.entity.ExcursionNeo;
import org.ors.server.entity.ExcursionNodeNeo;
import org.ors.server.entity.RoomNeo;
import org.ors.server.repository.ExcursionNeoRepository;
import org.ors.server.repository.ExcursionNodeNeoRepository;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExcursionService {

    ExcursionNeoRepository excursionRepo;
    ExcursionNodeNeoRepository excursionNodeRepo;
    RoomService roomService;
    CategoryService categoryService;

    @Autowired
    public ExcursionService(ExcursionNeoRepository excursionRepo, ExcursionNodeNeoRepository excursionStopRepo, RoomService roomService, CategoryService categoryService) {
        this.excursionRepo = excursionRepo;
        this.excursionNodeRepo = excursionStopRepo;
        this.roomService = roomService;
        this.categoryService = categoryService;
    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class}, readOnly = true)
    public List<Excursion> getAll(){
//        return StreamSupport.stream(excursionRepo.findAll().spliterator(), false).map(Excursion::new).collect(Collectors.toList());
        return excursionRepo.findAllFull().stream().map(Excursion::new).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class}, readOnly = true)
    public Excursion getOneById(String id)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        return new Excursion(getOneByIdNeo(id));
    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class}, readOnly = true)
    public ExcursionNeo getOneByIdNeo(String id)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        ExcursionNeo excursionNeo = excursionRepo.findByIdFull(Long.valueOf(id));
        if(excursionNeo == null) throw new DataNotFoundException("Excursion #"+id+" not found");
        return excursionNeo;
    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public Excursion addOne(Excursion excursion)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        ExcursionNeo excursionNeo = new ExcursionNeo(excursion);
        System.out.println(excursion.getCategory());
        if( excursion.getCategory() != null) {
            System.out.println("huh?");
            System.out.println(categoryService.getOneByName(excursion.getCategory().getName()));

            CategoryNeo categoryNeo = categoryService.getOneByNameNeo(excursion.getCategory().getName());
            System.out.println(categoryNeo + "\n" + (categoryNeo == null ? categoryNeo.getName() : ""));
            excursionNeo.setCategory(categoryNeo);
        }
        for( int i =0; i<excursion.getPath().size(); i++){
            RoomNeo room = roomService.getOneByIdNeo(excursion.getPath().get(i).getId());
            ExcursionNodeNeo nodeNeo = new ExcursionNodeNeo(i, excursionNeo, room);
            excursionNeo.addPathNode(nodeNeo);
        }
        excursionNeo = excursionRepo.save(excursionNeo);
        System.out.println("Final excursionNeo: " + excursionNeo.getCategory());
        return new Excursion(excursionNeo);
    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public List<Excursion> addMany(List<Excursion> excursionList)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        List<Excursion> newExcursions = new ArrayList<>(excursionList.size());
        for( Excursion excursion : excursionList )
            newExcursions.add(addOne(excursion));
        return newExcursions;
    }

    @Transactional(readOnly = true)
    public boolean existsById(String id){
        return excursionRepo.findById(Long.valueOf(id)).isPresent();
    }
}
