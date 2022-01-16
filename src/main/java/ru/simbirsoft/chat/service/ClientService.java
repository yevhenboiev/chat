package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Room;

import java.util.List;

public interface ClientService {
    ClientDto getById(Long clientId);

    Client getByLogin(String login);

    ClientDto save(CreateClientRequestDto clientRequestDto);

    ClientDto update(Long clientId, ClientDto clientDto);

    void deleteById(Long clientId);

    List<ClientDto> getAll();

    ClientDto blockedClient(Client client, Long timeInMinutes);

    ClientDto unblockedClient(Client client);

    ClientDto setModerator(Client client);

    ClientDto removeModerator(Client client);

    void checkBlockClient(Client expectedClient);

    boolean checkClientInRoom(Client expectedClient, Room expectedRoom);

    void checkCreatorRoomAndRoleAdminOrModerator(Client expectedClient, Room expectedRoom);

    void checkCreatorRoomAndRoleAdmin(Client expectedClient, Room expectedRoom);

}
