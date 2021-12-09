package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public List<MessageDto> getAllMessage() {
        return null;
    }

    @Override
    public List<MessageDto> getAllMessageByUserId(Long id) {
        return null;
    }

    @Override
    public MessageDto getMessageById(Long id) {
        return null;
    }

    @Override
    public MessageDto createMessage(CreateMessageRequestDto createMessageRequestDto) {
        return null;
    }

    @Override
    public MessageDto editMessage(Long id) {
        return null;
    }

    @Override
    public boolean deleteMessageById(Long id) {
        return false;
    }
}
