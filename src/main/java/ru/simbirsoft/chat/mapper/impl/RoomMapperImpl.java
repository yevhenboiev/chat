package ru.simbirsoft.chat.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.repository.MessageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoomMapperImpl implements RoomMapper {

    private final ClientRepository clientRepository;
    private final MessageRepository messageRepository;

    @Override
    public Room toEntity(RoomDto roomDto) {
        if (roomDto == null) {
            return null;
        }

        Room room = new Room();

        room.setId(roomDto.getId());
        room.setRoomName(roomDto.getRoomName());
        room.setPrivate(roomDto.isPrivate());
        room.setCreator(clientRepository.getById(roomDto.getCreator()));
        room.setMessages(longSetMessageIdToMessage(roomDto.getMessages()));
        room.setClientList(longSetClientIdToClient(roomDto.getClients()));

        return room;
    }

    @Override
    public Room toEntity(CreateRoomRequestDto createRoomRequestDto) {
        if (createRoomRequestDto == null) {
            return null;
        }

        Room room = new Room();

        room.setRoomName(createRoomRequestDto.getRoomName());
        room.setPrivate(createRoomRequestDto.isPrivate());

        return room;
    }

    @Override
    public RoomDto toDTO(Room room) {
        if (room == null) {
            return null;
        }

        RoomDto roomDto = new RoomDto();

        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setPrivate(room.isPrivate());
        roomDto.setCreator(room.getCreator().getId());
        roomDto.setMessages(messageToSetMessageId(room.getMessages()));
        roomDto.setClients(clientToSetClientId(room.getClientList()));

        return roomDto;
    }


    private List<Message> longSetMessageIdToMessage(Set<Long> longMessageId) {
        if (longMessageId == null) {
            return null;
        }

        List<Message> messageList = new ArrayList<>();

        for (Long longId : longMessageId) {
            messageList.add(messageRepository.getById(longId));
        }

        return messageList;
    }


    private Set<Client> longSetClientIdToClient(Set<Long> clients) {
        if (clients == null) {
            return null;
        }

        Set<Client> clientSet = new HashSet<>();

        for (Long longId : clients) {
            clientSet.add(clientRepository.getById(longId));
        }

        return clientSet;
    }

    private Set<Long> messageToSetMessageId(List<Message> messages) {
        if (messages == null) {
            return null;
        }

        Set<Long> longSet = new HashSet<>();

        for (Message message : messages) {
            longSet.add(message.getId());
        }

        return longSet;
    }

    private Set<Long> clientToSetClientId(Set<Client> clientList) {
        if (clientList == null) {
            return null;
        }

        Set<Long> longSet = new HashSet<>();

        for (Client client : clientList) {
            longSet.add(client.getId());
        }

        return longSet;
    }
}
