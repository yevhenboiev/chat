package ru.simbirsoft.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.chat.entity.enums.Role;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequestDto {
    private String name;
    private Set<Role> role;
    private boolean isBlock;
    private Timestamp startBan;
    private Timestamp endBan;
    private Set<RoomDto> clientRooms;
}
