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
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessageException;
import ru.simbirsoft.chat.mapper.MessageMapper;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.service.ClientService;
import ru.simbirsoft.chat.service.MessageService;
import ru.simbirsoft.chat.service.RoomService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final ClientService clientService;

    @Override
    @Transactional(readOnly = true)
    public MessageDto findMessageById(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new NotExistMessageException(messageId);
        }
        return messageMapper.toDTO(messageOptional.get());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDto save(User user, CreateMessageRequestDto messageRequestDto) {
        Client client = clientService.getByLogin(user.getUsername());
        Room room = roomMapper.toEntity(roomService.findRoomById(messageRequestDto.getRoomId()));
        Message message = messageMapper.toEntity(messageRequestDto);
        clientService.checkBlockClient(client);
        clientService.checkClientInRoom(client, room);
        message.setClient(client);
        message.setRoom(room);
        message.setCreationTime(Timestamp.valueOf(LocalDateTime.now()));
        return messageMapper.toDTO(messageRepository.save(message));
    }

    @Override
    @Transactional
    public MessageDto update(Long messageId, MessageDto messageDto) {
        findMessageById(messageId);
        Message message = messageMapper.toEntity(messageDto);
        message.setId(messageId);
        return messageMapper.toDTO(messageRepository.save(message));
    }

    @Override
    @Transactional
    public void deleteById(Long messageId) {
        findMessageById(messageId);
        messageRepository.deleteById(messageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getAllMessageInRoom(Room room) {
        return messageRepository.findAllByRoom(room).stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

}
