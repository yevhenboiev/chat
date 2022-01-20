package ru.simbirsoft.chat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateRoomRequestDto;
import ru.simbirsoft.chat.dto.RequestClientDto;
import ru.simbirsoft.chat.dto.RoomDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.entity.enums.Role;
import ru.simbirsoft.chat.exception.clientExceptions.ClientIsBlockedException;
import ru.simbirsoft.chat.exception.clientExceptions.ExistClientException;
import ru.simbirsoft.chat.exception.clientExceptions.NotAccessException;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.service.impl.ClientServiceImpl;
import ru.simbirsoft.chat.service.impl.RoomServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/create-user-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/drop-table-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientServiceTest {

    private final String password = "$2a$12$Gegfy7qb6lUqEjE15yEuYOh/pg7hHce0.cVztXkmry8pzwanuMHiO";

    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private RoomServiceImpl roomService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void getById() {
        Long clientId = clientService.getByLogin("Admin").getId();
        ClientDto clientDto = clientService.getById(clientId);

        assertEquals(clientDto.getName(), "Admin");
        assertEquals(clientDto.getRole(), Role.ADMIN);
        assertFalse(clientDto.isBlock());
        assertNull(clientDto.getStartBan());
        assertNull(clientDto.getEndBan());
    }

    @Test(expected = NotExistClientException.class)
    public void checkExpectedNotExistByGetById() {
        clientService.getById(100L);

    }

    @Test
    public void getByLogin() {
        Client client = clientService.getByLogin("Admin");

        assertEquals(client.getLogin(), "Admin");
        assertEquals(client.getRole(), Role.ADMIN);
        assertFalse(client.isBlock());
        assertNull(client.getStartBan());
        assertNull(client.getEndBan());
    }

    @Test(expected = NotExistClientException.class)
    public void checkExpectedNotExistInMethodGetByLogin() {
        clientService.getByLogin("DUMMY");
    }

    @Test
    public void save() {
        RequestClientDto client = new RequestClientDto();
        client.setLogin("Irina");
        client.setPassword(password);
        ClientDto clientDto = clientService.save(client);

        assertEquals(clientDto.getName(), "Irina");
        assertEquals(clientDto.getRole(), Role.USER);
        assertTrue(clientDto.isActive());
        assertFalse(clientDto.isBlock());
        assertNull(clientDto.getStartBan());
        assertNull(clientDto.getEndBan());
    }

    @Test(expected = ExistClientException.class)
    public void checkUniqueName() {
        RequestClientDto client = new RequestClientDto();
        client.setLogin("Bot");
        client.setPassword(password);
        clientService.save(client);
    }

    @Test
    public void updateMethod() {
        Long clientId = clientService.getByLogin("User_2").getId();
        ClientDto clientDto = clientService.getById(clientId);
        clientDto.setName("Marina");
        ClientDto updateClient = clientService.update(clientDto.getId(), clientDto);

        assertEquals(updateClient.getName(), "Marina");
        assertEquals(updateClient.getRole(), Role.USER);
        assertTrue(updateClient.isActive());
        assertFalse(updateClient.isBlock());
        assertNull(updateClient.getStartBan());
        assertNull(updateClient.getEndBan());
    }

    @Test(expected = NotExistClientException.class)
    public void checkDeleteMethod() {
        Long clientId = clientService.getByLogin("Bot").getId();
        clientService.deleteById(clientId);
        clientService.getByLogin("Bot");
    }

    @Test
    public void getAll() {
        List<ClientDto> clientDtoList = new ArrayList<>();
        Long clientId_1 = clientService.getByLogin("Bot").getId();
        Long clientId_2 = clientService.getByLogin("Admin").getId();
        Long clientId_3 = clientService.getByLogin("Moderator").getId();
        Long clientId_4 = clientService.getByLogin("User_1").getId();
        Long clientId_5 = clientService.getByLogin("User_2").getId();
        clientDtoList.add(clientService.getById(clientId_1));
        clientDtoList.add(clientService.getById(clientId_2));
        clientDtoList.add(clientService.getById(clientId_3));
        clientDtoList.add(clientService.getById(clientId_4));
        clientDtoList.add(clientService.getById(clientId_5));

        List<ClientDto> expectedClientDtoList = clientService.getAll();

        assertEquals(clientDtoList.size(), expectedClientDtoList.size());
    }

    @Test
    public void blockedClient() {
        Client client = clientService.getByLogin("User_2");
        clientService.blockedClient(client, 5L);

        assertTrue(client.isBlock());
        assertNotNull(client.getStartBan());
        assertNotNull(client.getEndBan());
    }

    @Test
    public void unblockedClient() {
        Client client = clientService.getByLogin("User_2");
        clientService.blockedClient(client, 5L);

        assertTrue(client.isBlock());
        assertNotNull(client.getStartBan());
        assertNotNull(client.getEndBan());

        clientService.unblockedClient(client);

        assertFalse(client.isBlock());
        assertNull(client.getStartBan());
        assertNull(client.getEndBan());
    }

    @Test
    public void setModerator() {
        Client client = clientService.getByLogin("User_1");

        assertEquals(client.getRole(), Role.USER);

        clientService.setModerator(client);

        assertEquals(client.getRole(), Role.MODERATOR);
    }

    @Test
    public void removeModerator() {
        Client client = clientService.getByLogin("Moderator");

        assertEquals(client.getRole(), Role.MODERATOR);

        clientService.removeModerator(client);

        assertEquals(client.getRole(), Role.USER);
    }

    @Test(expected = ClientIsBlockedException.class)
    public void checkBlockedClient() {
        Client client = clientService.getByLogin("User_2");
        clientService.blockedClient(client, 5L);

        clientService.checkBlockClient(client);
    }


    //TODO:org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: ru.simbirsoft.chat.entity.Client.clientRooms, could not initialize proxy - no Session
//    @Test
//    public void checkClientInRoom() {
//        RequestClientDto clientDto = new RequestClientDto("Login", password);
//        clientService.save(clientDto);
//        Room expectedRoom = roomService.findRoomByName("Chat Bot by Login");
//        Client expectedClient = clientService.getByLogin("Login");
//        Hibernate.initialize(expectedClient.getClientRooms());
//        Hibernate.initialize(expectedRoom.getClientList());
//
//        boolean clientInTheRoom = clientService.checkClientInRoom(expectedClient, expectedRoom);
//
//        assertTrue(clientInTheRoom);
//    }

    @Test(expected = NotExistRoomException.class)
    public void checkExceptionNotExistRoomInCheckClientInRoom() {
        Client expectedClient = clientService.getByLogin("Admin");
        Room expectedRoom = roomService.findRoomByName("Chat bot");
        boolean clientInTheRoom = clientService.checkClientInRoom(expectedClient, expectedRoom);

        assertFalse(clientInTheRoom);
    }

    @Test
    public void checkCreatorRoomOrAdminByAdmin() {
        Client client = clientService.getByLogin("Admin");
        UserDetails userDetails = userDetailsService.loadUserByUsername("User_1");
        User user = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        CreateRoomRequestDto createRoom = new CreateRoomRequestDto("new Room", false);
        RoomDto roomDto = roomService.save(user, createRoom);
        Room room = roomService.findRoomByName("new Room");

        clientService.checkCreatorRoomAndRoleAdmin(client, room);
    }

    @Test
    public void checkCreatorRoomOrAdminByCreator() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("User_1");
        User user = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        CreateRoomRequestDto createRoom = new CreateRoomRequestDto("new Room", false);
        roomService.save(user, createRoom);
        Room room = roomService.findRoomByName("new Room");
        Client client = clientService.getByLogin("User_1");

        clientService.checkCreatorRoomAndRoleAdmin(client, room);
    }

    @Test(expected = NotAccessException.class)
    public void checkExceptionNotAccessInCheckCreatorRoomOrAdminByNotCreator() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("User_1");
        User user = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        CreateRoomRequestDto createRoom = new CreateRoomRequestDto("new Room", false);
        roomService.save(user, createRoom);
        Room room = roomService.findRoomByName("new Room");
        Client client = clientService.getByLogin("User_2");

        clientService.checkCreatorRoomAndRoleAdmin(client, room);
    }
}