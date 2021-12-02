package ru.simbirsoft.chat.dto;

import ru.simbirsoft.chat.entity.Role;
import ru.simbirsoft.chat.entity.Room;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResponseClientDto {
    private Long ID;
    private String name;
    private Role role;
    private boolean isBlock;
    private Timestamp startBan;
    private Timestamp endBan;
    List<Room> clientRooms = new ArrayList<>();

    public ResponseClientDto() {
    }

    public ResponseClientDto(Long ID, String name, Role role, boolean isBlock, Timestamp startBan, Timestamp endBan, List<Room> clientRooms) {
        this.ID = ID;
        this.name = name;
        this.role = role;
        this.isBlock = isBlock;
        this.startBan = startBan;
        this.endBan = endBan;
        this.clientRooms = clientRooms;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Timestamp getStartBan() {
        return startBan;
    }

    public void setStartBan(Timestamp startBan) {
        this.startBan = startBan;
    }

    public Timestamp getEndBan() {
        return endBan;
    }

    public void setEndBan(Timestamp endBan) {
        this.endBan = endBan;
    }

    public List<Room> getClientRooms() {
        return clientRooms;
    }

    public void setClientRooms(List<Room> clientRooms) {
        this.clientRooms = clientRooms;
    }
}