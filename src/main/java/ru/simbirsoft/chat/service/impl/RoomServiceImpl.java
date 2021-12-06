package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.ResponseClientDto;
import ru.simbirsoft.chat.dto.ResponseRoomDto;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public List<ResponseRoomDto> getAll() {
        return null;
    }

    public ResponseRoomDto getById(Long id) {
        return null;
    }

    public ResponseRoomDto editRoom(Long id) {
        return null;
    }

    public boolean deleteRoomById(Long id) {
        return false;
    }
}
