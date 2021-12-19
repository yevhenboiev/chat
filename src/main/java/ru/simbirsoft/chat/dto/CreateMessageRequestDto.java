package ru.simbirsoft.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageRequestDto {

    private Long clientId;
    private Long roomId;
    private String content;
}
