package ru.simbirsoft.chat.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "creation_time")
    private Timestamp creationTime;

    @Column(name = "content")
    private String content;
}
