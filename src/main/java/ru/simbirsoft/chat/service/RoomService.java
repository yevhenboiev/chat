package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto getById(Long id);

    RoomDto save(CreateRoomRequestDto createRoomRequestDto);

    void deleteById(Long id);

    List<RoomDto> getAll();
}
