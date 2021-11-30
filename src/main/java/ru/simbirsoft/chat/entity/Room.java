package ru.simbirsoft.SimbirSoftPracticum.entity;


import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "room_name")
    private String room_name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_private")
    private boolean is_private;

    public Room() {
    }

    public Room(String room_name, User user, boolean is_private) {
        this.room_name = room_name;
        this.user = user;
        this.is_private = is_private;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }
}
