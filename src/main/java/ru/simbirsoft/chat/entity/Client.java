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

    @Column(name = "is_block")
    private boolean isBlock;

    @Column(name = "start_ban")
    private Timestamp startBan;

    @Column(name = "end_ban")
    private Timestamp endBan;

//    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL)
//    private List<Room> rooms = new ArrayList<>();

    public Client() {
    }

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

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

//    public List<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(List<Room> rooms) {
//        this.rooms = rooms;
//    }
}
