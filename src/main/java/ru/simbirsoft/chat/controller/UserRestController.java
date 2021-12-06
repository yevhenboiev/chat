package ru.simbirsoft.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.ResponseClientDto;
import ru.simbirsoft.chat.service.ClientService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final ClientService clientService;

    @GetMapping
    public ResponseClientDto getClient(@RequestParam Long id) {
        return null;
    }

    @PutMapping
    public ResponseClientDto createClient(@RequestBody CreateClientRequestDto requestClientDto) {
        return null;
    }

    @PatchMapping
    public ResponseClientDto editClient(@RequestBody CreateClientRequestDto requestClientDto) {
        return null;
    }

    @DeleteMapping
    public boolean deleteClient(@RequestParam Long id) {
        return false;
    }

}
