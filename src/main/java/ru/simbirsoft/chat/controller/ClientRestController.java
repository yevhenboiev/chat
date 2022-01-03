package ru.simbirsoft.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat.dto.ClientDto;
import ru.simbirsoft.chat.dto.CreateClientRequestDto;
import ru.simbirsoft.chat.exception.clientExceptions.NotExistClientException;
import ru.simbirsoft.chat.service.impl.ClientServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientRestController {

    private final ClientServiceImpl clientService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") @NotNull Long clientId) {
        ClientDto clientDto = clientService.getById(clientId);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@Valid @RequestBody CreateClientRequestDto createClientRequestDto) {
        ClientDto clientDto = clientService.save(createClientRequestDto);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClientDto> updateClientById(@PathVariable("id") @NotNull Long clientId, @Valid @RequestBody ClientDto clientDto) {
        ClientDto updateClientDto = clientService.update(clientId, clientDto);
        return new ResponseEntity<>(updateClientDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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

    @RequestMapping(value = "/blocked/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    public ResponseEntity<ClientDto> blockedClient(@PathVariable @NotNull Long id, Long timeInMinutes) {
        ClientDto clientDto = clientService.blockedClient(id, timeInMinutes);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/unblocked/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('MODERATOR')")
    public ResponseEntity<ClientDto> unblockedClient(@PathVariable @NotNull Long id) {
        ClientDto clientDto = clientService.unblockedClient(id);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/setmoderator/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientDto> setModerator(@PathVariable @NotNull Long id) {
        ClientDto clientDto = clientService.setModerator(id);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/removemoderator/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientDto> removeModerator(@PathVariable @NotNull Long id) {
        ClientDto clientDto = clientService.removeModerator(id);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }


}