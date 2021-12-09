package ru.simbirsoft.chat.dto;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private Long clientId;
    private Long roomId;
    private Timestamp time;
    private String content;
}
