package ru.simbirsoft.chat.mapper;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.RequestClientDto;
import ru.simbirsoft.chat.entity.Client;

//@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDto clientDto);

    Client toEntity(RequestClientDto requestClientDto);

    ClientDto toDTO(Client client);
}
