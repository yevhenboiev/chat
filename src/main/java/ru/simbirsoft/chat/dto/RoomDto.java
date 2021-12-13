package ru.simbirsoft.chat.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String roomName;
    private Long creatorId;
    private boolean isPrivate;
    private Set<MessageDto> clientList;
}
