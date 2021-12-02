package ru.simbirsoft.chat.dto;

import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.Client;

import java.sql.Timestamp;

public class ResponseMessageDto {
    private long id;
    private Client creator;
    private Room room;
    private Timestamp time;
    private String content;

    public ResponseMessageDto() {

    }

    public ResponseMessageDto(long id, Client creator, Room room, Timestamp time, String content) {
        this.id = id;
        this.creator = creator;
        this.room = room;
        this.time = time;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getCreator() {
        return creator;
    }

    public void setCreator(Client creator) {
        this.creator = creator;
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
