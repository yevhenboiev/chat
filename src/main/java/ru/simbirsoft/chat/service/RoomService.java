package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRooms();
    RoomDto getRoomById(Long id);
    RoomDto createRoom(CreateRoomRequestDto createRoomRequestDto);
    RoomDto editRoom(Long id);
    boolean deleteRoomById(Long id);
}
