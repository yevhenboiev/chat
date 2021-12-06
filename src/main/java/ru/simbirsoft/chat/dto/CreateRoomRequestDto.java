package ru.simbirsoft.chat.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateRoomRequestDto {
    private String roomName;
    private Long creatorId;
    private boolean isPrivate;
    private Set<ResponseMessageDto> clientList = new HashSet<>();
}
