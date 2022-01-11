package ru.simbirsoft.chat.mapper;

import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Message;

//@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toEntity(MessageDto messageDto);

    Message toEntity(CreateMessageRequestDto createMessageRequestDto);

    MessageDto toDTO(Message message);
}
