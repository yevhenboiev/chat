package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.exception.messageExceptions.NotExistMessageException;
import ru.simbirsoft.chat.service.impl.MessageServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageRestController {

    private final MessageServiceImpl messageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> getMessage(@PathVariable("id") @NotNull Long messageId) {
        MessageDto messageDto = messageService.findMessageById(messageId);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDto> saveMessage(@AuthenticationPrincipal User user,
                                                  @Valid @RequestBody CreateMessageRequestDto createMessageRequestDto) {
        MessageDto messageDto = messageService.save(user, createMessageRequestDto);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    public ResponseEntity<MessageDto> deleteMessageById(@PathVariable("id") @NotNull Long messageId) {
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getAllMessage() {
        List<MessageDto> allMessage = messageService.getAll();
        if (allMessage.isEmpty()) {
            throw new NotExistMessageException();
        }
        return new ResponseEntity<>(allMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MessageDto> updateMessageById(@PathVariable("id") @NotNull Long messageId,
                                                        @Valid @RequestBody MessageDto messageDto) {
        MessageDto updateMessageDto = messageService.update(messageId, messageDto);
        return new ResponseEntity<>(updateMessageDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{roomId}")
    public ResponseEntity<List<MessageDto>> getAllMessageInRoom(@PathVariable("roomId") @NotNull Room room) {
        List<MessageDto> allMessageInRoom = messageService.getAllMessageInRoom(room);
        if (allMessageInRoom.isEmpty()) {
            throw new NotExistMessageException();
        }
        return new ResponseEntity<>(allMessageInRoom, HttpStatus.OK);
    }

}
