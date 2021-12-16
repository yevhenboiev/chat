package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.exception.clientExceptions.ExistClient;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClient;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional(readOnly = true)
    @Override
    public ClientDto getById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new NotExistClient(id);
        }
        return clientMapper.toDTO(clientOptional.get());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ClientDto save(CreateClientRequestDto clientRequestDto) {
        if (clientRepository.findClientByName(clientRequestDto.getName()).isPresent()) {
            throw new ExistClient(clientRequestDto.getName());
        }
        return clientMapper.toDTO(clientRepository.save(clientMapper.toEntity(clientRequestDto)));
    }

    @Transactional
    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new NotExistClient(clientDto.getId());
        }
        Client client = clientMapper.toEntity(clientDto);
        client.setId(id);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new NotExistClient(id);
        }
        clientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }
}