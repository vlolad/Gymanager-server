package ru.gymanager.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.exception.NotFoundException;
import ru.gymanager.server.mapper.ClientMapper;
import ru.gymanager.server.model.ClientEntity;
import ru.gymanager.server.model.ClientTrainerLink;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.repository.ClientRepository;
import ru.gymanager.server.repository.ClientTrainerLinkRepository;
import ru.gymanager.server.service.AuthenticationInfoService;
import ru.gymanager.server.service.ClientService;
import ru.gymanager.server.service.UserService;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserService userService;
    private final ClientTrainerLinkRepository linkRepository;

    @Autowired
    public ClientServiceImpl (ClientRepository clientRepository, ClientMapper clientMapper,
                              UserService userService, ClientTrainerLinkRepository linkRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.userService = userService;
        this.linkRepository = linkRepository;
    }

    @Override
    @Transactional
    public ClientEntity createClient(ClientDto clientDto) {
        ClientEntity check = findClientByPhone(clientDto.getPhone());
        if (check != null) {
            return createLink(check);
        }
        ClientEntity client = clientMapper.toEntity(clientDto);
        client.setCreationDate(LocalDateTime.now());
        log.info("Create new client with phone={}", client.getPhone());
        client = clientRepository.save(client);
        return createLink(client);
    }

    //TODO find workouts
    @Override
    public ClientEntity getClient(String clientId) {
        return findClientById(clientId);
    }

    @Override
    public ClientEntity getClientByPhone(String phone) {
        ClientEntity client = findClientByPhone(phone);
        if (client == null) {
            throw new NotFoundException("Client with phone=" + phone + " not found.");
        }
        return client;
    }

    @Override
    public List<ClientEntity> getClients() {
        String trainerId = userService.findTrainerInfo(AuthenticationInfoService.getCurrentUserLogin()).getId();
        List<ClientTrainerLink> links = linkRepository.findAllByTrainerId(trainerId);
        if (links.isEmpty()) {
            return List.of();
        }
        return clientRepository.findAllByIdIn(links.stream()
                .map(ClientTrainerLink::getClientId).collect(Collectors.toList()));
    }

    @Transactional
    public ClientEntity createLink(ClientEntity client) {
        UserEntity trainer = userService.getUserByLogin(AuthenticationInfoService.getCurrentUserLogin());
        ClientTrainerLink link = new ClientTrainerLink(null, LocalDateTime.now(), trainer.getId(), client.getId());
        linkRepository.save(link);
        return client;
    }

    private ClientEntity findClientByPhone(String phone) {
        Optional<ClientEntity> client = clientRepository.findByPhone(phone);
        if (client.isPresent()) {
            log.info("Client with phone={} is found in DB.", phone);
            return client.get();
        } else {
            return null;
        }
    }

    private ClientEntity findClientById(String id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new NotFoundException("Client with id=" + id + " not found");
        }
        return client.get();
    }
}
