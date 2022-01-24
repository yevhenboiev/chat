package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.ChangeRoomNameDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable("id") @NotNull Long roomId) {
        RoomDto roomDto = roomService.findRoomById(roomId);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDto> saveRoom(@AuthenticationPrincipal User user, @Valid @RequestBody CreateRoomRequestDto createRoomRequestDto) {
        RoomDto roomDto = roomService.save(user, createRoomRequestDto);
        return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable("id") @NotNull Long roomId,
                                                  @Valid @RequestBody RoomDto roomDto) {
        RoomDto updateRoomDto = roomService.update(roomId, roomDto);
        return new ResponseEntity<>(updateRoomDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RoomDto> deleteRoomById(@AuthenticationPrincipal User user,
                                                  @PathVariable("id") @NotNull Room room) {
        roomService.deleteById(user, room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoom() {
        List<RoomDto> allRoom = roomService.getAll();
        if (allRoom.isEmpty()) {
            throw new NotExistRoomException();
        }
        return new ResponseEntity<>(allRoom, HttpStatus.OK);
    }

    @PutMapping(value = "/{roomId}/addUser/{clientId}")
    public ResponseEntity<RoomDto> addUserInListRoom(@AuthenticationPrincipal User user,
                                                     @PathVariable("roomId") @NotNull Room room,
                                                     @PathVariable("clientId") @NotNull Client client) {
        RoomDto roomDto = roomService.addUserInRoom(user, room, client);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{roomId}/removeUser/{clientId}")
    public ResponseEntity<RoomDto> removeUserInListRoom(@AuthenticationPrincipal User user,
                                                        @PathVariable("roomId") @NotNull Room room,
                                                        @PathVariable("clientId") @NotNull Client client) {
        RoomDto roomDto = roomService.removeUserInRoom(user, room, client);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{roomId}/rename")
    public ResponseEntity<RoomDto> renameRoom(@AuthenticationPrincipal User user,
                                              @PathVariable("roomId") @NotNull Room room,
                                              @Valid @RequestBody ChangeRoomNameDto changeRoomNameDto) {
        RoomDto roomDto = roomService.renameRoom(user, room, changeRoomNameDto);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }
}
