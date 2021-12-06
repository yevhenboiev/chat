package ru.simbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.ResponseClientDto;
import ru.simbirsoft.chat.entity.Client;

import java.util.List;

@Mapper
public interface ClientMapper {
    ClientMapper CLIENT_MAPPER = Mappers.getMapper(ClientMapper.class);
    Client toEntity(ResponseClientDto responseClientDto);
    Client toEntity(CreateClientRequestDto requestClientDto);
    ResponseClientDto toDTO(Client client);
    List<Client> allToEntity(List<ResponseClientDto> responseClientDtoList);
    List<ResponseClientDto> allToDTO(List<Client> clientList);

}
