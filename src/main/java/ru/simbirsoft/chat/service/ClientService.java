package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.ResponseClientDto;
import ru.simbirsoft.chat.exception.NotCorrect;
import ru.simbirsoft.chat.exception.NotFoundClient;

import java.util.List;

public interface ClientService {
    List<ResponseClientDto> getAll();
    ResponseClientDto getClientById(Long id);
    ResponseClientDto createClient(CreateClientRequestDto requestClientDto);
    ResponseClientDto editClient(ResponseClientDto responseClientDto) throws NotFoundClient;
    boolean deleteClient(Long id) throws NotCorrect;
}
