package ru.simbirsoft.chat.dto;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageRequestDto {

    private Long clientId;
    private Long roomId;
    private String content;
}
