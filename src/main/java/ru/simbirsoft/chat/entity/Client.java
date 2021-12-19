package ru.simbirsoft.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.chat.entity.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "client_role", joinColumns = @JoinColumn(name = "client_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> role;

    @Column(name = "is_block", nullable = false, columnDefinition = "boolean default false")
    private boolean isBlock;

    @Column(name = "start_ban")
    private Timestamp startBan;

    @Column(name = "end_ban")
    private Timestamp endBan;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "client_rooms",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")})
    private Set<Room> clientRooms;
}
