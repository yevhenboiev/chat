package ru.simbirsoft.chat.dto;

import ru.simbirsoft.chat.entity.Client;

public class ResponseRoomDto {
    private Long id;
    private String room_name;
    private Client user;
    private boolean is_private;

    public ResponseRoomDto() {
    }

    public ResponseRoomDto(Long id, String room_name, Client user, boolean is_private) {
        this.id = id;
        this.room_name = room_name;
        this.user = user;
        this.is_private = is_private;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }
}
