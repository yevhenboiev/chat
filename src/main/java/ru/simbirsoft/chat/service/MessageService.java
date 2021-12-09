package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto getById(Long id);

    MessageDto save(CreateMessageRequestDto messageRequestDto);

    void deleteById(Long id);

    List<MessageDto> getAll();
}
