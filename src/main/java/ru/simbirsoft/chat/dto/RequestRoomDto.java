package ru.simbirsoft.chat.dto;

import ru.simbirsoft.chat.entity.Client;

import java.util.List;


public class RequestRoomDto {

    private String roomName;
    private Client creator;
    private boolean isPrivate;
    private List<Client> clientList;

    public RequestRoomDto() {
    }

    public RequestRoomDto(String roomName, Client creator, boolean isPrivate, List<Client> clientList) {
        this.roomName = roomName;
        this.creator = creator;
        this.isPrivate = isPrivate;
        this.clientList = clientList;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Client getCreator() {
        return creator;
    }

    public void setCreator(Client creator) {
        this.creator = creator;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }
}
