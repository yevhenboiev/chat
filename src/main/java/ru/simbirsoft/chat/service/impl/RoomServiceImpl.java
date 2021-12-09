package ru.simbirsoft.chat.service.impl;

import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {


    @Override
    public RoomDto getById(Long id) {
        return null;
    }

    @Override
    public RoomDto save(CreateRoomRequestDto createRoomRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<RoomDto> getAll() {
        return null;
    }
}
