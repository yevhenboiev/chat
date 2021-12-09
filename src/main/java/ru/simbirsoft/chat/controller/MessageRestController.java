package ru.simbirsoft.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.service.impl.MessageServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private MessageServiceImpl messageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> getMessage(@PathVariable("id") Long messageId) {
        if (messageId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = messageService.getById(messageId);
        return messageDto == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDto> saveMessage(@RequestBody CreateMessageRequestDto createMessageRequestDto) {
        if (createMessageRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MessageDto messageDto = messageService.save(createMessageRequestDto);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MessageDto> updateMessageById(@PathVariable("id") Long messageId, @RequestBody CreateMessageRequestDto createMessageRequestDto) {
        if (messageId == null || createMessageRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MessageDto messageDto = messageService.save(createMessageRequestDto);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageDto> deleteMessageById(@PathVariable("id") Long id) {
        MessageDto messageDto = messageService.getById(id);
        if (messageDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        messageService.deleteById(id);
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
