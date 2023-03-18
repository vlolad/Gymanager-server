package ru.gymanager.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.dto.ClientDtoShort;
import ru.gymanager.server.dto.WorkoutShortDto;
import ru.gymanager.server.mapper.ClientMapper;
import ru.gymanager.server.model.ClientEntity;
import ru.gymanager.server.service.ClientService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
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
    public ClientDto createClient(@RequestBody ClientDto clientDto) {
        ClientEntity clientEntity = clientService.createClient(clientDto);
        return clientMapper.toDto(clientEntity);
    }

    @GetMapping("/all")
    public List<ClientDtoShort> getShortClients(@RequestParam(defaultValue = "10") Integer size,
                                                @RequestParam(defaultValue = "0") Integer page) {
        //TODO заменить мок на функционал
        List<ClientDtoShort> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ClientDtoShort client = ClientDtoShort.builder()
                    .id("01" + i)
                    .firstName("Dummy" + i)
                    .phone("+7800555353" + i)
                    .build();
            result.add(client);
        }

        return result;
    }

    @GetMapping("/{clientId}")
    public ClientDto getClient(@PathVariable String clientId) {
        //TODO заменить мок на функционал
        List<WorkoutShortDto> workouts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            WorkoutShortDto workout = WorkoutShortDto.builder()
                    .id("0123" + i)
                    .startDate(LocalDateTime.now().plusHours(i))
                    .description("Train #" + i)
                    .build();
            workouts.add(workout);
        }
        return ClientDto.builder()
                .id(clientId)
                .firstName("Dummy")
                .phone("+78005553535")
                .description("Simple mock-client. Have only one leg and crutch.")
                .workouts(workouts)
                .build();
    }
}
