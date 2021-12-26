package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.mapper.ClientMapper;
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
    private final ClientServiceImpl clientService;
    private final ClientMapper clientMapper;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    @Override
    public RoomDto getById(Long roomId) {
        return roomMapper.toDTO(foundRoomOrExceptionById(roomId));
    }

    @Transactional
    @Override
    public RoomDto save(CreateRoomRequestDto createRoomRequestDto) {
        Client client = clientMapper.toEntity(clientService.getById(createRoomRequestDto.getCreatorId()));
        Room room = roomMapper.toEntity(createRoomRequestDto);
        room.setCreator(client);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public RoomDto update(Long roomId, RoomDto roomDto) {
        foundRoomOrExceptionById(roomId);
        Room room = roomMapper.toEntity(roomDto);
        room.setId(roomId);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public void deleteById(Long roomId) {
        foundRoomOrExceptionById(roomId);
        roomRepository.deleteById(roomId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomDto> getAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Room foundRoomOrExceptionById(Long searchKey) {
        Optional<Room> roomOptional = roomRepository.findById(searchKey);
        if (roomOptional.isEmpty()) {
            throw new NotExistRoomException(searchKey);
        }
        return roomOptional.get();
    }
}
