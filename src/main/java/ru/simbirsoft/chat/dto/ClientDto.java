package ru.simbirsoft.chat.dto;

import lombok.Data;
import ru.simbirsoft.chat.entity.Role;
import ru.simbirsoft.chat.entity.Room;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
public class ClientDto{
    private Long id;
    private String name;
    private Role role;
    private boolean isBlock;
    private Timestamp startBan;
    private Timestamp endBan;
    private Set<Room> clientRooms = new HashSet<>();
}
