package ru.simbirsoft.chat.dto;

import lombok.Data;
import ru.simbirsoft.chat.entity.Client;

import java.util.HashSet;
import java.util.Set;

@Data
public class RoomDto {
    private Long id;
    private String roomName;
    private Client creator;
    private boolean isPrivate;
    private Set<Client> clientList = new HashSet<>();
}
