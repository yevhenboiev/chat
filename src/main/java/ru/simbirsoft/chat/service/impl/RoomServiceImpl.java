package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Transactional
    @Override
    public RoomDto getById(Long id) {
        return roomMapper.toDTO(roomRepository.getById(id));
    }

    @Transactional
    @Override
    public RoomDto save(CreateRoomRequestDto createRoomRequestDto) {
        return roomMapper.toDTO(roomRepository.save(roomMapper.toEntity(createRoomRequestDto)));
    }

    @Transactional
    @Override
    public RoomDto update(RoomDto roomDto) {
        return roomMapper.toDTO(roomRepository.save(roomMapper.toEntity(roomDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<RoomDto> getAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }
}
