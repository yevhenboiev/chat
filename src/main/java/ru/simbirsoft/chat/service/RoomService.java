package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto getById(Long roomId);

    RoomDto save(CreateRoomRequestDto createRoomRequestDto);

    RoomDto update(Long roomId, RoomDto roomDto);

    void deleteById(Long roomId);

    List<RoomDto> getAll();
}
