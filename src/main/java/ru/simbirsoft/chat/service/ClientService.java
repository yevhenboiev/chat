package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.exception.NotCorrect;
import ru.simbirsoft.chat.exception.NotFoundClient;

import java.util.List;

public interface ClientService {
    List<ClientDto> getAllClients();
    ClientDto getClientById(Long id);
    ClientDto createClient(CreateClientRequestDto requestClientDto);
    ClientDto editClient(ClientDto responseClientDto);
    boolean deleteClient(Long id) throws NotCorrect;
}
