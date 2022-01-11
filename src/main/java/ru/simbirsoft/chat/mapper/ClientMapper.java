package ru.simbirsoft.chat.mapper;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.entity.Client;

//@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDto clientDto);

    Client toEntity(CreateClientRequestDto createClientRequestDto);

    ClientDto toDTO(Client client);
}
