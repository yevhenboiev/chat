package ru.simbirsoft.chat.service;

import org.springframework.security.core.userdetails.User;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Room;

import java.util.List;

public interface MessageService {
    MessageDto findMessageById(Long messageId);

    MessageDto save(User user, CreateMessageRequestDto messageRequestDto);

    MessageDto update(Long messageId, MessageDto messageDto);

    void deleteById(Long messageId);

    List<MessageDto> getAll();

    List<MessageDto> getAllMessageInRoom(Room room);
}
