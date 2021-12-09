package ru.simbirsoft.chat.service.impl;

import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public MessageDto getById(Long id) {
        return null;
    }

    @Override
    public MessageDto save(CreateMessageRequestDto messageRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public List<MessageDto> getAll() {
        return null;
    }
}
