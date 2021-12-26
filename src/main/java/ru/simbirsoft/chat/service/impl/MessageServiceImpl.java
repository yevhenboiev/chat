package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessageException;
import ru.simbirsoft.chat.mapper.ClientMapper;
import ru.simbirsoft.chat.mapper.MessageMapper;
import ru.simbirsoft.chat.mapper.RoomMapper;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomServiceImpl roomService;
    private final ClientServiceImpl clientService;
    private final RoomMapper roomMapper;
    private final ClientMapper clientMapper;
    private final MessageMapper messageMapper;

    @Override
    @Transactional(readOnly = true)
    public MessageDto getById(Long messageId) {
        return messageMapper.toDTO(foundMessageOrExceptionById(messageId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDto save(CreateMessageRequestDto messageRequestDto) {
        Client client = clientMapper.toEntity(clientService.getById(messageRequestDto.getClientId()));
        Room room = roomMapper.toEntity(roomService.getById(messageRequestDto.getRoomId()));
        Message message = messageMapper.toEntity(messageRequestDto);
        message.setClient(client);
        message.setRoom(room);
        return messageMapper.toDTO(messageRepository.save(message));
    }

    @Override
    @Transactional
    public MessageDto update(Long messageId, MessageDto messageDto) {
        foundMessageOrExceptionById(messageId);
        Message message = messageMapper.toEntity(messageDto);
        message.setId(messageId);
        return messageMapper.toDTO(messageRepository.save(message));
    }

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
        if (messageOptional.isEmpty()) {
            throw new NotExistMessageException(searchKey);
        }
        return messageOptional.get();
    }
}
