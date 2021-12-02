package ru.simbirsoft.chat.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private Role role;

    @Column(name = "is_block")
    private boolean isBlock;

    @Column(name = "start_ban")
    private Timestamp startBan;

    @Column(name = "end_ban")
    private Timestamp endBan;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "client_rooms",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    List<Room> clientRooms = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
