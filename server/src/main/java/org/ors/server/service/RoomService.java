package org.ors.server.service;

import org.ors.server.dto.Room;
import org.ors.server.entity.RoomMongo;
import org.ors.server.entity.RoomNeo;
import org.ors.server.repository.RoomMongoRepository;
import org.ors.server.repository.RoomNeoRepository;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomMongoRepository mongoRepo;
    private RoomNeoRepository neoRepo;

    @Autowired
    public RoomService(RoomMongoRepository mongoRepo, RoomNeoRepository neoRepo) {
        this.mongoRepo = mongoRepo;
        this.neoRepo = neoRepo;
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public Room addOne(Room room)
        throws DataIntegrityViolationException
    {
        RoomMongo roomMongo = mongoRepo.save(new RoomMongo(room));
        RoomNeo roomNeo = neoRepo.save(new RoomNeo(roomMongo));
        return new Room(roomNeo);
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public List<Room> addMany(List<Room> roomList)
        throws DataIntegrityViolationException
    {
        ArrayList<Room> newRoomList = new ArrayList<>(roomList.size());
        for(Room r : roomList){
            newRoomList.add(addOne(r));
        }
        return newRoomList;
    }

    @Transactional(readOnly = true, rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public Room getOneById(String id)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        return new Room(getOneByIdNeo(id));
    }

    @Transactional(readOnly = true, rollbackFor = {DataIntegrityViolationException.class, DataNotFoundException.class})
    public RoomNeo getOneByIdNeo(String id)
        throws DataIntegrityViolationException, DataNotFoundException
    {
        RoomNeo roomNeo = neoRepo.findByMongoid(id);
        if( roomNeo == null ) throw new DataNotFoundException("Room #" + id + " not found");
        return roomNeo;
    }


    @Transactional(readOnly = true, rollbackFor = {DataIntegrityViolationException.class})
    public List<Room> getAll()
        throws DataIntegrityViolationException
    {
        return mongoRepo.findAll().stream().map(Room::new).collect(Collectors.toList());
    }
}
