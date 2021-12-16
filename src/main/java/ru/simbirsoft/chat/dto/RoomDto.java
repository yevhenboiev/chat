package ru.simbirsoft.chat.dto;

import lombok.*;
import ru.simbirsoft.chat.entity.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String roomName;
    private ClientDto creator;
    private boolean isPrivate;
    private List<MessageDto> messages;
    private Set<ClientDto> clients;
}
