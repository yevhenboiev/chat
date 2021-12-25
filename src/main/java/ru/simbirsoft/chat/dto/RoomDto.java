package ru.simbirsoft.chat.dto;

import lombok.*;
import ru.simbirsoft.chat.entity.Client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    @NotBlank
    private String roomName;
    @NotNull
    private ClientDto creator;
    @NotNull
    private boolean isPrivate;
    private List<MessageDto> messages;
    private Set<ClientDto> clients;
}
