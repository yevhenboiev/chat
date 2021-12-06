package ru.simbirsoft.chat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "room_name")
    private String roomName;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client creator;

    @Column(name = "is_private")
    private boolean isPrivate;

    @ManyToMany(mappedBy = "clientRooms")
    private Set<Client> clientList = new HashSet<>();
}
