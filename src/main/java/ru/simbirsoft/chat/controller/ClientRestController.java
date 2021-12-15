package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.service.impl.ClientServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientRestController {

    private final ClientServiceImpl clientService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") Long clientId) {
        if(clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientDto clientDto = clientService.getById(clientId);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@Validated @RequestBody CreateClientRequestDto createClientRequestDto) {
        if (createClientRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientDto clientDto = clientService.save(createClientRequestDto);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClientDto> updateClientById(@PathVariable("id") Long clientId, @Validated @RequestBody ClientDto clientDto) {
        if (clientId == null || clientDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientDto updateClientDto = clientService.update(clientId, clientDto);
        return new ResponseEntity<>(updateClientDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientDto> deleteClientById(@PathVariable("id") Long clientId) {
        if(clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        clientService.deleteById(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClient() {
        List<ClientDto> allClient = clientService.getAll();
        if (allClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allClient, HttpStatus.OK);
    }
}