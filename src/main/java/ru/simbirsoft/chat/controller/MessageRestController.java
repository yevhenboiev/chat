package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.service.impl.MessageServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageRestController {

    private final MessageServiceImpl messageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> getMessage(@PathVariable("id") Long messageId) {
        if (messageId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = messageService.getById(messageId);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDto> saveMessage(@Validated @RequestBody CreateMessageRequestDto createMessageRequestDto) {
        if (createMessageRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = messageService.save(createMessageRequestDto);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MessageDto> updateMessageById(@Validated @PathVariable("id") Long messageId, @RequestBody MessageDto messageDto) {
        if (messageId == null || messageDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto updateMessageDto = messageService.update(messageId, messageDto);
        return new ResponseEntity<>(updateMessageDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageDto> deleteMessageById(@PathVariable("id") Long messageId) {
        if (messageId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getAllMessage() {
        List<MessageDto> allMessage = messageService.getAll();
        if (allMessage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allMessage, HttpStatus.OK);
    }
}
