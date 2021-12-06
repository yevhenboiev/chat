package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.RequestClientDto;
import ru.simbirsoft.chat.dto.ResponseClientDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.exception.NotCorrect;
import ru.simbirsoft.chat.exception.NotFoundClient;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.service.ClientService;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<ResponseClientDto> getAll() {
        return allToDTO(clientRepository.findAll());
    }

    @Override
    public ResponseClientDto getClientById(Long id) {
        return toDTO(clientRepository.getById(id));
    }

    @Override
    public ResponseClientDto createClient(RequestClientDto requestClientDto) {
        return toDTO(clientRepository.save(toEntity(requestClientDto)));
    }

    @Override
    public ResponseClientDto editClient(ResponseClientDto responseClientDto) throws NotFoundClient {
        if(responseClientDto.getId() == null) {
            throw new NotFoundClient("Not found Client " + responseClientDto.getName());
        }
        return toDTO(clientRepository.save(toEntity(responseClientDto)));
    }

    @Override
    public boolean deleteClient(Long id) throws NotCorrect {
        if(id == null) {
            throw new NotCorrect(id + " - not correct ID");
        }
        if(clientRepository.findById(id).isPresent()) {
            clientRepository.deleteById(id);
            return clientRepository.findById(id).isEmpty();
        }
        return false;
    }

    private Client toEntity(ResponseClientDto responseClientDto) {
        return ClientMapper.CLIENT_MAPPER.toEntity(responseClientDto);
    }

    private Client toEntity(RequestClientDto requestClientDto) {
        return ClientMapper.CLIENT_MAPPER.toEntity(requestClientDto);
    }

    private ResponseClientDto toDTO(Client client) {
        return ClientMapper.CLIENT_MAPPER.toDTO(client);
    }

    private List<Client> allToEntity(List<ResponseClientDto> responseClientDtoList) {
        return ClientMapper.CLIENT_MAPPER.allToEntity(responseClientDtoList);
    }

    private List<ResponseClientDto> allToDTO(List<Client> clientList) {
        return ClientMapper.CLIENT_MAPPER.allToDTO(clientList);
    }
}