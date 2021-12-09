package ru.simbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.entity.Client;

import java.util.List;

@Mapper
public interface ClientMapper {
    ClientMapper CLIENT_MAPPER = Mappers.getMapper(ClientMapper.class);
    Client toEntity(ClientDto ClientDto);
    Client toEntity(CreateClientRequestDto createClientRequestDto);
    ClientDto toDTO(Client client);
    List<Client> allToEntity(List<ClientDto> responseClientDtoList);
    List<ClientDto> allToDTO(List<Client> clientList);

}
