package ru.simbirsoft.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequestDto {
    @NotBlank
    @Size(min = 3, max = 55)
    private String roomName;
    @NotNull
    private Long creatorId;
    @NotNull
    private boolean isPrivate;
}
