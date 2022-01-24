package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.TimeBannedDto;
import ru.simbirsoft.chat.entity.Client;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.service.ClientService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") @NotNull Long clientId) {
        ClientDto clientDto = clientService.getById(clientId);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDto> updateClientById(@PathVariable("id") @NotNull Long clientId, @Valid @RequestBody ClientDto clientDto) {
        ClientDto updateClientDto = clientService.update(clientId, clientDto);
        return new ResponseEntity<>(updateClientDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClientDto> deleteClientById(@PathVariable("id") @NotNull Long clientId) {
        clientService.deleteById(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClient() {
        List<ClientDto> allClient = clientService.getAll();
        if (allClient.isEmpty()) {
            throw new NotExistClientException();
        }
        return new ResponseEntity<>(allClient, HttpStatus.OK);
    }

    @PutMapping(value = "/blocked/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    public ResponseEntity<ClientDto> blockedClient(@PathVariable("id") @NotNull Client client,
                                                   @Valid @RequestBody TimeBannedDto timeBannedDto) {
        ClientDto clientDto = clientService.blockedClient(client, timeBannedDto.getTimeInMinutes());
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PutMapping(value = "/unblocked/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    public ResponseEntity<ClientDto> unblockedClient(@PathVariable("id") @NotNull Client client) {
        ClientDto clientDto = clientService.unblockedClient(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PutMapping(value = "/set-moderator/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientDto> setModerator(@PathVariable("id") @NotNull Client client) {
        ClientDto clientDto = clientService.setModerator(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PutMapping(value = "/remove-moderator/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientDto> removeModerator(@PathVariable("id") @NotNull Client client) {
        ClientDto clientDto = clientService.removeModerator(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }


}