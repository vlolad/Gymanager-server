package ru.gymanager.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.dto.ClientDtoShort;
import ru.gymanager.server.mapper.ClientMapper;
import ru.gymanager.server.model.ClientEntity;
import ru.gymanager.server.service.ClientService;
import ru.gymanager.server.util.validate.Create;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/client")
@Validated
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient(@RequestBody @Validated(Create.class) ClientDto clientDto) {
        ClientEntity clientEntity = clientService.createClient(clientDto);
        return clientMapper.toDto(clientEntity);
    }

    @GetMapping("/all")
    public List<ClientDtoShort> getShortClients() {
        return clientMapper.toDtoShortList(clientService.getClients());
    }

    @GetMapping("/{clientId}")
    public ClientDto getClient(@PathVariable @NotBlank String clientId) {
        return clientMapper.toDto(clientService.getClient(clientId));
    }

    @GetMapping("phone/{phone}")
    public ClientDto getClientByPhone(@PathVariable @NotBlank String phone) {
        return clientMapper.toDto(clientService.getClientByPhone(phone));
    }
}
