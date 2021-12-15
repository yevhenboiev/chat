package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;

import java.util.List;

public interface ClientService {
    ClientDto getById(Long id);

    ClientDto save(CreateClientRequestDto clientRequestDto);

    ClientDto update(Long id, ClientDto clientDto);

    void deleteById(Long id);

    List<ClientDto> getAll();

}
