package ru.simbirsoft.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.service.ClientService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final ClientService clientService;

    @GetMapping
    public ClientDto getClient(@RequestParam Long id) {
        return null;
    }

    @PutMapping
    public ClientDto createClient(@RequestBody CreateClientRequestDto createClientRequestDto) {
        return null;
    }

    @PatchMapping
    public ClientDto updateClient(@RequestBody CreateClientRequestDto createClientRequestDto) {
        return null;
    }

    @DeleteMapping
    public boolean deleteClient(@RequestParam Long id) {
        return false;
    }

}
