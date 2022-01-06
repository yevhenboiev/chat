package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.ChangeRoomNameDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.enums.Role;
import ru.simbirsoft.chat.exception.clientExceptions.ClientIsBlockedException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;
    private final ClientServiceImpl clientService;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    @Override
    public RoomDto getById(Long roomId) {
        return roomMapper.toDTO(foundRoomOrExceptionById(roomId));
    }

    @Transactional
    @Override
    public RoomDto save(User user, CreateRoomRequestDto createRoomRequestDto) {
        Client client = clientService.getByLogin(user.getUsername());
        Room room = roomMapper.toEntity(createRoomRequestDto);
        room.setCreator(client);
        client.getClientRooms().add(room);
        clientRepository.save(client);
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

    @Transactional
    @Override
    public RoomDto addUserInRoom(User user, Room room, Client client) {
        Client currentUser = clientService.getByLogin(user.getUsername());
        if (currentUser.isBlock()) {
            throw new ClientIsBlockedException(currentUser.getName(), currentUser.getEndBan());
        }
        if (currentUser.equals(room.getCreator()) || currentUser.getRole().equals(Role.ADMIN) || currentUser.getRole().equals(Role.MODERATOR)) {
            client.getClientRooms().add(room);
            clientRepository.save(client);
        }
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public RoomDto removeUserInRoom(User user, Room room, Client client) {
        Client currentUser = clientService.getByLogin(user.getUsername());
        if (currentUser.isBlock()) {
            throw new ClientIsBlockedException(currentUser.getName(), currentUser.getEndBan());
        }
        if (currentUser.equals(room.getCreator()) || currentUser.getRole().equals(Role.ADMIN) || currentUser.getRole().equals(Role.MODERATOR)) {
            client.getClientRooms().remove(room);
            clientRepository.save(client);
        }
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public RoomDto renameRoom(User user, Room room, ChangeRoomNameDto roomNameDto) {
        Client currentUser = clientService.getByLogin(user.getUsername());
        if (currentUser.isBlock()) {
            throw new ClientIsBlockedException(currentUser.getName(), currentUser.getEndBan());
        }
        if (currentUser.equals(room.getCreator()) || currentUser.getRole().equals(Role.ADMIN)) {
            room.setRoomName(roomNameDto.getRoomName());
            roomRepository.save(room);
        }
        return roomMapper.toDTO(room);
    }


    private Room foundRoomOrExceptionById(Long searchKey) {
        Optional<Room> roomOptional = roomRepository.findById(searchKey);
        if (!roomOptional.isPresent()) {
            throw new NotExistRoomException(searchKey);
        }
        return roomOptional.get();
    }


}
