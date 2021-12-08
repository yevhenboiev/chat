package ru.simbirsoft.chat.dto;

import lombok.Data;
import ru.simbirsoft.chat.entity.Role;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
public class CreateClientRequestDto {
    private String name;
    private Set<Role> role;
    private boolean isBlock;
    private Timestamp startBan;
    private Timestamp endBan;
    private Set<RoomDto> clientRooms = new HashSet<>();
}
