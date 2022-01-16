package ru.simbirsoft.chat.service;

import org.springframework.security.core.userdetails.User;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Room;

public interface BotRoomService {
    MessageDto processingRequest(User user, Room room, CreateMessageRequestDto createMessageRequestDto);
}
