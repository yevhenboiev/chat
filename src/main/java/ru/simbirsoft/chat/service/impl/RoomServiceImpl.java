package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.ChangeRoomNameDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.roomExceptions.ExistRoomException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.ClientService;
import ru.simbirsoft.chat.service.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Transactional(readOnly = true)
    @Override
    public RoomDto findRoomById(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (!roomOptional.isPresent()) {
            throw new NotExistRoomException(roomId);
        }
        return roomMapper.toDTO(roomOptional.get());
    }

    @Override
    public Room findRoomByName(String name) {
        Optional<Room> roomOptional = roomRepository.findRoomByRoomName(name);
        if (!roomOptional.isPresent()) {
            throw new NotExistRoomException();
        }
        return roomOptional.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public RoomDto save(User user, CreateRoomRequestDto createRoomRequestDto) {
        Client client = clientService.getByLogin(user.getUsername());
        Room room = roomMapper.toEntity(createRoomRequestDto);
        if (roomRepository.findRoomByRoomName(room.getRoomName()).isPresent()) {
         throw new ExistRoomException(room.getRoomName());
        }
        clientService.checkBlockClient(client);
        room.setCreator(client);
        roomRepository.save(room);
        client.getClientRooms().add(room);
        clientService.update(client.getId(), clientMapper.toDTO(client));
        return roomMapper.toDTO(room);
    }

    @Transactional
    @Override
    public RoomDto update(Long roomId, RoomDto roomDto) {
        findRoomById(roomId);
        Room room = roomMapper.toEntity(roomDto);
        room.setId(roomId);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public void deleteById(User user, Room room) {
        Client client = clientService.getByLogin(user.getUsername());
        clientService.checkBlockClient(client);
        findRoomById(room.getId());
        clientService.checkCreatorRoomAndRoleAdmin(client, room);
        for(Client disconnectedClient : room.getClientList()) {
            removeUserInRoom(user, room, disconnectedClient);
        }
        roomRepository.deleteById(room.getId());
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
    public RoomDto addUserInRoom(User user, Room room, Client addedClient) {
        if (room.isPrivate()) {
            Client client = clientService.getByLogin(user.getUsername());
            clientService.checkBlockClient(client);
            clientService.checkCreatorRoomAndRoleAdminOrModerator(client, room);
        }
        addedClient.getClientRooms().add(room);
        clientService.update(addedClient.getId(), clientMapper.toDTO(addedClient));
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public RoomDto removeUserInRoom(User user, Room room, Client removedClient) {
        removedClient.getClientRooms().remove(room);
        clientService.update(removedClient.getId(), clientMapper.toDTO(removedClient));
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Transactional
    @Override
    public RoomDto renameRoom(User user, Room room, ChangeRoomNameDto roomNameDto) {
        Client client = clientService.getByLogin(user.getUsername());
        clientService.checkBlockClient(client);
        clientService.checkCreatorRoomAndRoleAdmin(client, room);
        room.setRoomName(roomNameDto.getRoomName());
        return roomMapper.toDTO(roomRepository.save(room));
    }
}
