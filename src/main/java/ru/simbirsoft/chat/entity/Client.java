package ru.simbirsoft.chat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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
    Set<Room> clientRooms = new HashSet<>();
}
