package ru.simbirsoft.chat.dto;

import lombok.*;
import ru.simbirsoft.chat.entity.Role;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private Set<Role> role;
    private boolean isBlock;
    private Timestamp startBan;
    private Timestamp endBan;
    private Set<RoomDto> clientRooms;
}
