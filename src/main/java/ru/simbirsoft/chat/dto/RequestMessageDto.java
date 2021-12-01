package ru.simbirsoft.chat.dto;

import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.User;

import java.sql.Timestamp;

public class RequestMessageDto {
    private User user;
    private Room room;
    private Timestamp time;
    private String content;

    public RequestMessageDto() {
    }

    public RequestMessageDto(User user, Room room, Timestamp time, String content) {
        this.user = user;
        this.room = room;
        this.time = time;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
