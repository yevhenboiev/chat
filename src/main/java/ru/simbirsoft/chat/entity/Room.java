package ru.simbirsoft.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "Room name is mandatory")
    @Column(name = "room_name")
    private String roomName;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client creator;

    @Column(name = "is_private")
    private boolean isPrivate;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "client_messages")
    private List<Message> messages;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "clientRooms")
    @Column(name = "client_rooms")
    private Set<Client> clientList;
}
