package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.RequestClientDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.enums.Role;
import ru.simbirsoft.chat.exception.clientExceptions.ClientIsBlockedException;
import ru.simbirsoft.chat.exception.clientExceptions.ExistClientException;
import ru.simbirsoft.chat.exception.clientExceptions.NotAccessException;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.ClientService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    @Override
    public ClientDto getById(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new NotExistClientException(clientId);
        }
        return clientMapper.toDTO(clientOptional.get());
    }

    @Transactional
    @Override
    public Client getByLogin(String login) {
        Optional<Client> clientOptional = clientRepository.findByLogin(login);
        if (!clientOptional.isPresent()) {
            throw new NotExistClientException(login);
        }
        return clientOptional.get();
    }

    @Transactional
    @Override
    public ClientDto save(RequestClientDto clientRequestDto) {
        if (clientRepository.findByLogin(clientRequestDto.getLogin()).isPresent()) {
            throw new ExistClientException(clientRequestDto.getLogin());
        }
        Room chatBot = createChatBot(clientRequestDto.getLogin());
        Client client = clientMapper.toEntity(clientRequestDto);
        client.setPassword(passwordEncoder.encode(clientRequestDto.getPassword()));
        client.setRole(Role.USER);
        Set<Room> roomSet = new HashSet<>();
        roomSet.add(chatBot);
        client.setClientRooms(roomSet);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto update(Long clientId, ClientDto clientDto) {
        getById(clientId);
        Client client = clientMapper.toEntity(clientDto);
        client.setId(clientId);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public void deleteById(Long clientId) {
        ClientDto clientDto = getById(clientId);
        clientDto.setClientRooms(null);
        update(clientId, clientDto);
        clientRepository.deleteById(clientId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientDto blockedClient(Client client, Long timeInMinutes) {
        client.setBlock(true);
        LocalDateTime timeNow = LocalDateTime.now();
        client.setStartBan(Timestamp.valueOf(LocalDateTime.now()));
        client.setEndBan(Timestamp.valueOf(timeNow.plusMinutes(timeInMinutes)));
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto unblockedClient(Client client) {
        client.setBlock(false);
        client.setStartBan(null);
        client.setEndBan(null);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto setModerator(Client client) {
        if (client.getRole() == Role.USER) {
            client.setRole(Role.MODERATOR);
        }
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto removeModerator(Client client) {
        if (client.getRole() == Role.MODERATOR) {
            client.setRole(Role.USER);
        }
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public void checkBlockClient(Client expectedClient) {
        if (expectedClient.isBlock()) {
            if (expectedClient.getEndBan().compareTo(Timestamp.valueOf(LocalDateTime.now())) > 0) {
                throw new ClientIsBlockedException(expectedClient.getLogin(), expectedClient.getEndBan());
            } else {
                unblockedClient(expectedClient);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public boolean checkClientInRoom(Client expectedClient, Room expectedRoom) {
        for (Room room : expectedClient.getClientRooms()) {
            if (room.getId() == expectedRoom.getId()) {
                return true;
            }
        }
        throw new NotExistRoomException(expectedRoom.getRoomName());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public void checkCreatorRoomAndRoleAdminOrModerator(Client expectedClient, Room expectedRoom) {
        if (expectedClient.getId().equals(expectedRoom.getCreator().getId()) && !expectedClient.getRole().equals(Role.ADMIN)
                && !expectedClient.getRole().equals(Role.MODERATOR)) {
            throw new NotAccessException(expectedClient.getLogin());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public void checkCreatorRoomAndRoleAdmin(Client expectedClient, Room expectedRoom) {
        if (!expectedClient.getId().equals(expectedRoom.getCreator().getId()) && !expectedClient.getRole().equals(Role.ADMIN)) {
            throw new NotAccessException(expectedClient.getLogin());
        }
    }


    private Room createChatBot(String clientName) {
        Client bot = getByLogin("Bot");
        Room room = new Room();
        room.setRoomName("Chat Bot by " + clientName);
        room.setPrivate(true);
        room.setCreator(bot);
        bot.getClientRooms().add(room);
        return roomRepository.save(room);
    }
}