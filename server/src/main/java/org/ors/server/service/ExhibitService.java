package org.ors.server.service;

import org.ors.server.dto.Author;
import org.ors.server.dto.Exhibit;
import org.ors.server.entity.*;
import org.ors.server.repository.ExhibitMongoRepository;
import org.ors.server.repository.ExhibitNeoRepository;
import org.ors.server.repository.RelocationCasRepository;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExhibitService {
    private ExhibitMongoRepository mongoRepo;
    private ExhibitNeoRepository neoRepo;

    private RelocationCasRepository relocationCasRepository;

    private RoomService roomService;

    @Autowired
    public ExhibitService(ExhibitMongoRepository mongoRepo, ExhibitNeoRepository neoRepo, RelocationCasRepository relocationCasRepository, RoomService roomService) {
        this.mongoRepo = mongoRepo;
        this.neoRepo = neoRepo;
        this.relocationCasRepository = relocationCasRepository;
        this.roomService = roomService;
    }

    @Transactional(readOnly = true)
    public List<Exhibit> getAll(){
        return new ArrayList<>(
            StreamSupport.stream(neoRepo.findAll().spliterator(), false).map(Exhibit::new).collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public Exhibit getOneById(String id)
        throws DataNotFoundException
    {
        return new Exhibit(getOneByIdNeo(id));
    }

    @Transactional(readOnly = true)
    ExhibitNeo getOneByIdNeo(String id)
        throws DataNotFoundException
    {
        ExhibitNeo exhibitNeo = this.neoRepo.findByMongoid(id);
        if(exhibitNeo == null ) throw new DataNotFoundException("Exhibit #"+id+" not found");
        return exhibitNeo;
    }

    private Exhibit _addOne(Exhibit exhibit){
        ExhibitMongo exhibitMongo = this.mongoRepo.save(new ExhibitMongo(exhibit));
        ExhibitNeo exhibitNeo = this.neoRepo.save(new ExhibitNeo(exhibitMongo));
        return new Exhibit(exhibitNeo);
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Exhibit addOne(Exhibit exhibit){
        return _addOne(exhibit);
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public List<Exhibit> addMany(List<Exhibit> exhibits){
        List<Exhibit> newExhibits = new ArrayList<>(exhibits.size());
        for( Exhibit exhibit: exhibits){
            newExhibits.add((_addOne(exhibit)));
        }
        return newExhibits;
    }

    @Transactional(readOnly = true)
    public boolean existsById(String id){
        return neoRepo.existsByMongoid(id) != null;
    }

//    @Transactional(rollbackFor = { DataIntegrityViolationException.class, DataNotFoundException.class })
//    public Exhibit place(String exhibitId, String roomId)
//        throws DataNotFoundException
//    {
//        ExhibitNeo exhibitNeo = neoRepo.findByMongoid(exhibitId);
//        if( exhibitNeo == null ) throw new DataNotFoundException("Exhibit #"+exhibitId+" not found");
//        RoomNeo roomNeo = roomService.getOneByIdNeo(roomId);
//        if( roomNeo == null ) throw new DataNotFoundException("Room #"+roomId + " not found");
//        exhibitNeo.setRoom(roomNeo);
//        exhibitNeo = neoRepo.save(exhibitNeo);
//        return new Exhibit(exhibitNeo);
//    }

    @Transactional(rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public Exhibit relocate(String exhibitId, String newRoomId)
        throws DataNotFoundException, DataIntegrityViolationException
    {
        String from = "";
        ExhibitNeo exhibitNeo = neoRepo.findByMongoid(exhibitId);
        if( exhibitNeo == null ) throw new DataNotFoundException("Exhibit #"+exhibitId+" not found");
        if( exhibitNeo.getRoom() != null ) from = exhibitNeo.getRoom().getMongoid();
        RoomNeo roomNeo = roomService.getOneByIdNeo(newRoomId);
        if( roomNeo == null ) throw new DataNotFoundException("Room #"+newRoomId+ " not found");
        exhibitNeo.setRoom(roomNeo);
        RelocationCas relocationCas = new RelocationCas(exhibitId, from, roomNeo.getMongoid());
        relocationCasRepository.save(relocationCas);
        exhibitNeo = neoRepo.save(exhibitNeo);
        return new Exhibit(exhibitNeo);
    }
}
