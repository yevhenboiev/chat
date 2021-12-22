package ru.simbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.security.SecurityUser;

@Mapper(componentModel = "spring")

public interface UserDetailMapper {
    SecurityUser toSecurityUser(Client client);
}
