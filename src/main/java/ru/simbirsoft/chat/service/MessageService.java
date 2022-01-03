package ru.simbirsoft.chat.service;

import org.springframework.security.core.userdetails.User;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto getById(User user, Long messageId);

    MessageDto save(CreateMessageRequestDto messageRequestDto);

    MessageDto update(Long messageId, MessageDto messageDto);

    void deleteById(Long messageId);

    List<MessageDto> getAll();
}
