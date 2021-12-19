package ru.simbirsoft.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @NotBlank
    @JoinColumn(name = "client_id")
    private Client client;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Column(name = "creation_time")
    @CreationTimestamp
    private Timestamp creationTime;

    @NotBlank
    @Column(name = "content", nullable = false)
    private String content;
}
