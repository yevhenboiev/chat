package ru.simbirsoft.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.service.impl.RoomServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {

    @Autowired
    private RoomServiceImpl roomService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoomDto> getRoom(@PathVariable("id") Long roomId) {
        if (roomId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RoomDto roomDto = roomService.getById(roomId);
        return roomDto == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDto> saveRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto) {
        if (createRoomRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RoomDto roomDto = roomService.save(createRoomRequestDto);
        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable("id") Long roomId, @RequestBody CreateRoomRequestDto createRoomRequestDto) {
        if (roomId == null || createRoomRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RoomDto roomDto = roomService.save(createRoomRequestDto);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable("id") Long id) {
        RoomDto roomDto = roomService.getById(id);
        if (roomDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoom() {
        List<RoomDto> allRoom = roomService.getAll();
        if (allRoom.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allRoom, HttpStatus.OK);
    }
}
