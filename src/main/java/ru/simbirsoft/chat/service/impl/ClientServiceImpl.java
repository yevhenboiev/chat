package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.enums.Role;
import ru.simbirsoft.chat.exception.clientExceptions.ExistClientException;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.service.ClientService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Client foundClientOrExceptionById(Long searchKey) {
        Optional<Client> clientOptional = clientRepository.findById(searchKey);
        if (clientOptional.isEmpty()) {
            throw new NotExistClientException(searchKey);
        }
        return clientOptional.get();
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDto getById(Long clientId) {
        return clientMapper.toDTO(foundClientOrExceptionById(clientId));
    }

    @Override
    public Client getByLogin(String login) {
        Optional<Client> clientOptional = clientRepository.findByLogin(login);
        if (clientOptional.isEmpty()) {
            throw new NotExistClientException(login);
        }
        return clientOptional.get();
    }

    @Transactional
    @Override
    public ClientDto save(CreateClientRequestDto clientRequestDto) {
        if (clientRepository.findByLogin(clientRequestDto.getLogin()).isPresent()) {
            throw new ExistClientException(clientRequestDto.getLogin());
        }
        Client client = clientMapper.toEntity(clientRequestDto);
        client.setPassword(passwordEncoder.encode(clientRequestDto.getPassword()));
        client.setRole(Role.USER);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto update(Long clientId, ClientDto clientDto) {
        foundClientOrExceptionById(clientId);
        Client client = clientMapper.toEntity(clientDto);
        client.setId(clientId);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public void deleteById(Long clientId) {
        foundClientOrExceptionById(clientId);
        clientRepository.deleteById(clientId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientDto blockedClient(Long clientId, Long timeInHours) {
        Client client = foundClientOrExceptionById(clientId);
        client.setBlock(true);
        LocalDateTime timeNow = LocalDateTime.now();
        client.setStartBan(Timestamp.valueOf(LocalDateTime.now()));
        client.setEndBan(Timestamp.valueOf(timeNow.plusHours(timeInHours)));
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto unblockedClient(Long clientId) {
        Client client = foundClientOrExceptionById(clientId);
        client.setBlock(false);
        client.setStartBan(null);
        client.setEndBan(null);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public ClientDto setModerator(Long id) {
        Client client = foundClientOrExceptionById(id);
        if (client.getRole() == Role.USER) {
            client.setRole(Role.MODERATOR);
        }
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Override
    public ClientDto removeModerator(Long id) {
        Client client = foundClientOrExceptionById(id);
        if (client.getRole() == Role.MODERATOR) {
            client.setRole(Role.USER);
        }
        return clientMapper.toDTO(clientRepository.save(client));
    }
}