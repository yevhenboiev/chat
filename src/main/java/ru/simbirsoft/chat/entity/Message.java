package ru.simbirsoft.chat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "content")
    private String content;
}
