package ru.simbirsoft.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "room_name")
    private String roomName;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client creator;

    @Column(name = "is_private")
    private boolean isPrivate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @Column(name = "client_messages")
    private List<Message> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "clientRooms", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH})
    @Column(name = "client_rooms")
    private Set<Client> clientList = new HashSet<>();
}
