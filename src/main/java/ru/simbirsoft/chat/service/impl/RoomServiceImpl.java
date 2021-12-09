package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public List<RoomDto> getAllRooms() {
        return null;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        return null;
    }

    @Override
    public RoomDto createRoom(CreateRoomRequestDto createRoomRequestDto) {
        return null;
    }

    @Override
    public RoomDto editRoom(Long id) {
        return null;
    }

    @Override
    public boolean deleteRoomById(Long id) {
        return false;
    }
}
