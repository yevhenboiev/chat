package ru.simbirsoft.chat.dto;

import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.Client;

import java.sql.Timestamp;

public class RequestMessageDto {
    private Client client;
    private Room room;
    private Timestamp time;
    private String content;

    public RequestMessageDto() {
    }

    public RequestMessageDto(Client client, Room room, Timestamp time, String content) {
        this.client = client;
        this.room = room;
        this.time = time;
        this.content = content;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
