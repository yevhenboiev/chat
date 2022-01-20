package ru.simbirsoft.chat.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.RequestClientDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.repository.RoomRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClientMapperImpl implements ClientMapper {

    private final RoomRepository roomRepository;

    @Override
    public Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }

        Client client = new Client();

        client.setId(clientDto.getId());
        client.setLogin(clientDto.getName());
        client.setPassword(clientDto.getPassword());
        client.setRole(clientDto.getRole());
        client.setBlock(clientDto.isBlock());
        client.setActive(clientDto.isActive());
        client.setStartBan(clientDto.getStartBan());
        client.setEndBan(clientDto.getEndBan());
        client.setClientRooms(longRoomIdToRoom(clientDto.getClientRooms()));

        return client;
    }

    @Override
    public Client toEntity(RequestClientDto requestClientDto) {
        if (requestClientDto == null) {
            return null;
        }

        Client client = new Client();

        client.setLogin(requestClientDto.getLogin());
        client.setPassword(requestClientDto.getPassword());
        client.setActive(true);
        client.setBlock(false);

        return client;
    }

    @Override
    public ClientDto toDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setId(client.getId());
        clientDto.setName(client.getLogin());
        clientDto.setPassword(client.getPassword());
        clientDto.setRole(client.getRole());
        clientDto.setBlock(client.isBlock());
        clientDto.setActive(client.isActive());
        clientDto.setStartBan(client.getStartBan());
        clientDto.setEndBan(client.getEndBan());
        clientDto.setClientRooms(roomToSetLongRoomId(client.getClientRooms()));

        return clientDto;
    }

    private Set<Room> longRoomIdToRoom(Set<Long> longSet) {
        if (longSet == null) {
            return null;
        }

        Set<Room> roomSet = new HashSet<>();

        for (Long id : longSet) {
            roomSet.add(roomRepository.getById(id));
        }

        return roomSet;
    }

    private Set<Long> roomToSetLongRoomId(Set<Room> roomSet) {
        if (roomSet == null) {
            return null;
        }

        Set<Long> longSet = new HashSet<>();

        for (Room room : roomSet) {
            longSet.add(room.getId());
        }

        return longSet;
    }
}
