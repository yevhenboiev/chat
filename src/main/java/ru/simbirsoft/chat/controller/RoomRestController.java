package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.service.impl.RoomServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomRestController {

    private final RoomServiceImpl roomService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoomDto> getRoom(@PathVariable("id") @NotNull Long roomId) {
        RoomDto roomDto = roomService.getById(roomId);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDto> saveRoom(@Valid @RequestBody CreateRoomRequestDto createRoomRequestDto) {
        RoomDto roomDto = roomService.save(createRoomRequestDto);
        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable("id") @NotNull Long roomId, @Valid @RequestBody RoomDto roomDto) {
        RoomDto updateRoomDto = roomService.update(roomId, roomDto);
        return new ResponseEntity<>(updateRoomDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable("id") @NotNull Long roomId) {
        roomService.deleteById(roomId);
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
