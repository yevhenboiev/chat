package ru.simbirsoft.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Long creator;
    @NotNull
    private boolean isPrivate;
    private Set<Long> messages;
    private Set<Long> clients;
}
