package ru.simbirsoft.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Message;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClient;
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessage;
import ru.simbirsoft.chat.mapper.MessageMapper;
import ru.simbirsoft.chat.repository.MessageRepository;
import ru.simbirsoft.chat.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto getById(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if(messageOptional.isEmpty()) {
            throw new NotExistMessage(id);
        }
        return messageMapper.toDTO(messageOptional.get());
    }

    //TODO: CREATE ANOTHER EXCEPTION
    @Override
    public MessageDto save(CreateMessageRequestDto messageRequestDto) {
        return messageMapper.toDTO(messageRepository.save(messageMapper.toEntity(messageRequestDto)));
    }

    @Override
    public MessageDto update(Long messageId, MessageDto messageDto) {
        return messageMapper.toDTO(messageRepository.save(messageMapper.toEntity(messageDto)));
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageDto> getAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
