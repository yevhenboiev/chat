package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateMessageRequestDto;
import ru.simbirsoft.chat.dto.MessageDto;
import ru.simbirsoft.chat.entity.Room;
import ru.simbirsoft.chat.service.BotRoomService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bot/{id}")
public class BotRoomRestController {

    private final BotRoomService botRoomService;

    @PostMapping()
    public ResponseEntity<?> requestMessage(@AuthenticationPrincipal User user,
                                            @PathVariable("id") @NotNull Room room,
                                            @Valid @RequestBody CreateMessageRequestDto createMessageRequestDto) {
        MessageDto messageDto = botRoomService.processingRequest(user, room, createMessageRequestDto);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
}
