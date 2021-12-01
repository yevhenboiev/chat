package ru.simbirsoft.chat.dto;

import java.sql.Timestamp;

public class RequestUserDto {

    private String name;
    private boolean is_block;
    private Timestamp start_ban;
    private Timestamp end_ban;

    public RequestUserDto() {
    }

    public RequestUserDto(String name, boolean is_block, Timestamp start_ban, Timestamp end_ban) {
        this.name = name;
        this.is_block = is_block;
        this.start_ban = start_ban;
        this.end_ban = end_ban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_block() {
        return is_block;
    }

    public void setIs_block(boolean is_block) {
        this.is_block = is_block;
    }

    public Timestamp getStart_ban() {
        return start_ban;
    }

    public void setStart_ban(Timestamp start_ban) {
        this.start_ban = start_ban;
    }

    public Timestamp getEnd_ban() {
        return end_ban;
    }

    public void setEnd_ban(Timestamp end_ban) {
        this.end_ban = end_ban;
    }
}
