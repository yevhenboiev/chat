package ru.simbirsoft.chat.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.ResponseMessageDto;
import ru.simbirsoft.chat.dto.ResponseRoomDto;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.service.MessageService;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public List<ResponseMessageDto> getAll() {
        return null;
    }

    public ResponseMessageDto getById(Long id) {
        return null;
    }

    public ResponseMessageDto editMessage(Long id) {
        return null;
    }

    public boolean deleteMessageById(Long id) {
        return false;
    }
}
