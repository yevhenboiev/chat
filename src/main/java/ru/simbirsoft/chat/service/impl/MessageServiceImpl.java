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
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessage;
import ru.simbirsoft.chat.mapper.MessageMapper;
import ru.simbirsoft.chat.repository.ClientRepository;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.repository.RoomRepository;
import ru.simbirsoft.chat.service.MessageService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;
    private final MessageMapper messageMapper;

    @Override
    @Transactional(readOnly = true)
    public MessageDto getById(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isEmpty()) {
            throw new NotExistMessage(id);
        }
        return messageMapper.toDTO(messageOptional.get());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDto save(CreateMessageRequestDto messageRequestDto) {
        Client client = clientRepository.getById(messageRequestDto.getClientId());
        Room room = roomRepository.getById(messageRequestDto.getRoomId());
        Message message = messageMapper.toEntity(messageRequestDto);
        message.setClient(client);
        message.setRoom(room);
        message.setCreationTime(Timestamp.valueOf(LocalDateTime.now()));
        return messageMapper.toDTO(messageRepository.save(messageMapper.toEntity(messageRequestDto)));
    }

    @Override
    @Transactional
    public MessageDto update(Long messageId, MessageDto messageDto) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isEmpty()) {
            throw new NotExistMessage(messageId);
        }
        Message message = messageMapper.toEntity(messageDto);
        message.setId(messageId);
        return messageMapper.toDTO(messageRepository.save(message));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(!messageRepository.existsById(id)) {
            throw new NotExistMessage(id);
        }
        messageRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
