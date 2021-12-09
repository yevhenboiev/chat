package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private  ClientRepository clientRepository;

    @Override
    public ClientDto getById(Long id) {
        return ClientMapper.CLIENT_MAPPER.toDTO(clientRepository.getById(id));
    }

    @Override
    public ClientDto save(CreateClientRequestDto clientRequestDto) {
        return ClientMapper.CLIENT_MAPPER.toDTO(clientRepository.save(ClientMapper.CLIENT_MAPPER.toEntity(clientRequestDto)));
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<ClientDto> getAll() {
        return ClientMapper.CLIENT_MAPPER.allToDTO(clientRepository.findAll());
    }
}