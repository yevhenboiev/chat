package ru.simbirsoft.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.service.impl.ClientServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private ClientServiceImpl clientService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") Long clientId) {
        if (clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientDto clientDto = clientService.getById(clientId);
        return clientDto == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@Valid @RequestBody CreateClientRequestDto createClientRequestDto) {
        if (createClientRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ClientDto clientDto = clientService.save(createClientRequestDto);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClientDto> updateClientById(@Valid @PathVariable("id") Long clientId, @RequestBody CreateClientRequestDto createClientRequestDto) {
        if (clientId == null || createClientRequestDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientDto clientDto = clientService.save(createClientRequestDto);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientDto> deleteClientById(@PathVariable("id") Long id) {
        ClientDto clientDto = clientService.getById(id);
        if (clientDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientService.deleteById(id);
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
