package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.clientExceptions.ClientIsBlockedException;
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessageException;
import ru.simbirsoft.chat.exception.roomExceptions.NotExistRoomException;
import ru.simbirsoft.chat.mapper.MessageMapper;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final RoomRepository roomRepository;
    private final ClientServiceImpl clientService;

    @Override
    @Transactional(readOnly = true)
    public MessageDto getById(User user, Message message) {
        Client client = clientService.getByLogin(user.getUsername());
        if (client.getClientRooms().add(message.getRoom())) {
            throw new NotExistRoomException(message.getRoom().getId());
        }
        return messageMapper.toDTO(message);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDto save(User user, CreateMessageRequestDto messageRequestDto) {
        Client client = clientService.getByLogin(user.getUsername());
        if (client.isBlock()) {
            throw new ClientIsBlockedException(client.getName(), client.getEndBan());
        }
        Room currentRoom = roomRepository.getById(messageRequestDto.getRoomId());
        if (client.getClientRooms().add(currentRoom)) {
            throw new NotExistRoomException(currentRoom.getId());
        }
        Message message = messageMapper.toEntity(messageRequestDto);
        message.setClient(client);
        message.setRoom(currentRoom);
        return messageMapper.toDTO(messageRepository.save(message));
    }

//    @Override
//    @Transactional
//    public MessageDto update(Long messageId, MessageDto messageDto) {
//        foundMessageOrExceptionById(messageId);
//        Message message = messageMapper.toEntity(messageDto);
//        message.setId(messageId);
//        return messageMapper.toDTO(messageRepository.save(message));
//    }

    @Override
    @Transactional
    public void deleteById(Long messageId) {
        foundMessageOrExceptionById(messageId);
        messageRepository.deleteById(messageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Message foundMessageOrExceptionById(Long searchKey) {
        Optional<Message> messageOptional = messageRepository.findById(searchKey);
        if (!messageOptional.isPresent()) {
            throw new NotExistMessageException(searchKey);
        }
        return messageOptional.get();
    }
}
