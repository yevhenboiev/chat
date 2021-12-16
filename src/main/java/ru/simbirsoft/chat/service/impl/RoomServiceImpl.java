package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoom;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    @Override
    public RoomDto getById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isEmpty()) {
            throw new NotExistRoom(id);
        }
        return roomMapper.toDTO(roomOptional.get());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public RoomDto save(CreateRoomRequestDto createRoomRequestDto) {
        return roomMapper.toDTO(roomRepository.save(roomMapper.toEntity(createRoomRequestDto)));
    }

    @Transactional
    @Override
    public RoomDto update(Long roomId, RoomDto roomDto) {
        if(!roomRepository.existsById(roomId)) {
            throw new NotExistRoom(roomId);
        }
        Room room = roomMapper.toEntity(roomDto);
        room.setId(roomId);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if(!roomRepository.existsById(id)) {
            throw new NotExistRoom(id);
        }
        roomRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomDto> getAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }
}
