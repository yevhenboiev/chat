package ru.simbirsoft.chat.dto;

import lombok.Data;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;

import java.sql.Timestamp;

@Data
public class MessageDto {
    private Long id;
    private Client client;
    private Room room;
    private Timestamp time;
    private String content;
}
