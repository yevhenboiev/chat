package ru.simbirsoft.chat.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.mapper.MessageMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.repository.RoomRepository;

@Component
@RequiredArgsConstructor
public class MessageMapperImpl implements MessageMapper {

    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;


    @Override
    public Message toEntity(MessageDto messageDto) {
        if (messageDto == null) {
            return null;
        }

        Message message = new Message();

        message.setId(messageDto.getId());
        message.setClient(clientRepository.getById(messageDto.getClientId()));
        message.setRoom(roomRepository.getById(messageDto.getRoomId()));
        message.setContent(messageDto.getContent());

        return message;
    }

    @Override
    public Message toEntity(CreateMessageRequestDto createMessageRequestDto) {
        if (createMessageRequestDto == null) {
            return null;
        }

        Message message = new Message();

        message.setContent(createMessageRequestDto.getContent());

        return message;
    }

    @Override
    public MessageDto toDTO(Message message) {
        if (message == null) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId(message.getId());
        messageDto.setClientId(message.getClient().getId());
        messageDto.setRoomId(message.getRoom().getId());
        messageDto.setTime(message.getCreationTime());
        messageDto.setContent(message.getContent());

        return messageDto;
    }
}
