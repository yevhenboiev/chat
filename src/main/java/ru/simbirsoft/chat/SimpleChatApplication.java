//package ru.simbirsoft.chat;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import ru.simbirsoft.chat.entity.Client;
//import ru.simbirsoft.chat.entity.Room;
//import ru.simbirsoft.chat.repository.ClientRepository;
//import ru.simbirsoft.chat.repository.MessageRepository;
//import ru.simbirsoft.chat.repository.RoomRepository;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@SpringBootApplication
//public class SimpleChatApplication implements CommandLineRunner {
//    ClientRepository clientRepository;
//    MessageRepository messageRepository;
//    RoomRepository roomRepository;
//
//    @Autowired
//    public SimpleChatApplication(ClientRepository clientRepository, MessageRepository messageRepository, RoomRepository roomRepository) {
//        this.clientRepository = clientRepository;
//        this.messageRepository = messageRepository;
//        this.roomRepository = roomRepository;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(SimpleChatApplication.class, args);
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        Client client1 = new Client();
//        client1.setName("Oleg");
//        client1.setBlock(false);
//        Client client2 = new Client();
//        client2.setName("Igor");
//        client2.setBlock(false);
//        Client client3 = new Client();
//        client3.setName("Olga");
//        client3.setBlock(false);
//
//        Room room = new Room();
//        room.setRoomName("New Room Chat");
//        room.setPrivate(false);
//        room.setCreator(client1);
//
//        Set<Room> rooms = new HashSet<>();
//        rooms.add(room);
//        client1.setClientRooms(rooms);
//        client2.setClientRooms(rooms);
//
//        Set<Client> clients = new HashSet<>();
//        clients.add(client1);
//        clients.add(client2);
//        room.setClientList(clients);
//
//        clientRepository.save(client1);
//        clientRepository.save(client2);
//        roomRepository.save(room);
//
//        client1.setClientRooms(new HashSet<>());
//        clientRepository.save(client1);
//
//        System.out.println("All user: ");
//        clientRepository.findAll().forEach(System.out::println);
//        System.out.println("__________");
//        System.out.println("All room: ");
//        roomRepository.findAll().forEach(System.out::println);
//        System.out.println("_______deleting________");
//    }
//}