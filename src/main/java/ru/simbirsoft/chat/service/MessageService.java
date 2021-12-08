package ru.simbirsoft.chat.service;

import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getAllMessage();
    List<MessageDto> getAllMessageByUserId(Long id);
    MessageDto getMessageById(Long id);
    MessageDto createMessage(CreateMessageRequestDto createMessageRequestDto);
    MessageDto editMessage(Long id);
    boolean deleteMessageById(Long id);
}
