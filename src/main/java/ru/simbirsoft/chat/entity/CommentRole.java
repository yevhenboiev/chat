//package ru.simbirsoft.chat.entity;
//
//import ru.simbirsoft.chat.entity.util.EnumRole;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "role")
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "CLIENT_ID")
//    private Client name;
//
//    @Column(name = "role_name")
//    private EnumRole role;
//
//    public Role() {}
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Client getName() {
//        return name;
//    }
//
//    public void setName(Client name) {
//        this.name = name;
//    }
//
//    public EnumRole getRole() {
//        return role;
//    }
//
//    public void setRole(EnumRole role) {
//        this.role = role;
//    }
//}
