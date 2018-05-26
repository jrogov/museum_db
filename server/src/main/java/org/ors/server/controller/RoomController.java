package org.ors.server.controller;

import org.ors.server.dto.DTOList;
import org.ors.server.dto.ErrorDTO;
import org.ors.server.dto.IDTO;
import org.ors.server.dto.Room;
import org.ors.server.service.RoomService;
import org.ors.server.util.annotations.GetJsonMapping;
import org.ors.server.util.annotations.PostJsonMapping;
import org.ors.server.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController
{
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostJsonMapping("/api/room")
    public ResponseEntity<IDTO> addRoom(@RequestBody Room r){
        return ResponseEntity.ok(roomService.addOne(r));
    }

    @PostJsonMapping("/api/room/many")
    public ResponseEntity<IDTO> addRooms(@RequestBody DTOList<Room> roomList){
        return ResponseEntity.ok(new DTOList<>(roomService.addMany(roomList)));
    }

    @GetJsonMapping("/api/room")
    public ResponseEntity<IDTO> getAllRooms(){
        return ResponseEntity.ok(new DTOList<>(roomService.getAll()));
    }

    @GetJsonMapping("/api/room/{id}")
    public ResponseEntity<IDTO> getRoom(@PathVariable String id){
        try{
            return ResponseEntity.ok(roomService.getOneById(id));
        }
        catch (DataNotFoundException e){
            return ErrorDTO.response(e.getMessage());
        }
    }
}
