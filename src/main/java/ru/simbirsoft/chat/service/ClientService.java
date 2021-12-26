package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.entity.Client;

import java.util.List;

public interface ClientService {
    ClientDto getById(Long clientId);

    Client getByLogin(String login);

    ClientDto save(CreateClientRequestDto clientRequestDto);

    ClientDto update(Long clientId, ClientDto clientDto);

    void deleteById(Long clientId);

    List<ClientDto> getAll();

}
