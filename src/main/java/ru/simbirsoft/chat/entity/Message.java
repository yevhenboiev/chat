package ru.simbirsoft.chat.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Client client;

    @ManyToOne
    private Room room;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private Timestamp creationTime;

    @Column(name = "content", nullable = false, length = 1024)
    private String content;
}
