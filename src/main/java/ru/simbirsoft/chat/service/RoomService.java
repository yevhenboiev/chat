package ru.simbirsoft.chat.service;

import org.springframework.security.core.userdetails.User;
import ru.simbirsoft.chat.dto.ChangeRoomNameDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;

import java.util.List;

public interface RoomService {
    RoomDto findRoomById(Long roomId);

    Room findRoomByName(String name);

    RoomDto save(User user, CreateRoomRequestDto createRoomRequestDto);

    RoomDto update(Long roomId, RoomDto roomDto);

    void deleteById(User user, Room room);

    List<RoomDto> getAll();

    RoomDto addUserInRoom(User user, Room room, Client client);

    RoomDto removeUserInRoom(User user, Room room, Client client);

    RoomDto renameRoom(User user, Room room, ChangeRoomNameDto roomNameDto);
}
