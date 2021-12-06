package ru.simbirsoft.chat.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RequestMessageDto {

    private Long clientId;
    private Long roomId;
    private Timestamp time;
    private String content;
}
