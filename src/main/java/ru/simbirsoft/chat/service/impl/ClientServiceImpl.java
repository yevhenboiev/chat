package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.service.ClientService;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public List<ClientDto> getAllClients() {
        return null;
    }

    @Override
    public ClientDto getClientById(Long id) {
        return null;
    }

    @Override
    public ClientDto createClient(CreateClientRequestDto requestClientDto) {
        return null;
    }

    @Override
    public ClientDto editClient(ClientDto responseClientDto) {
        return null;
    }
}