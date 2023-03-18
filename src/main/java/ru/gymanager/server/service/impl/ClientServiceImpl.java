package ru.gymanager.server.service.impl;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.model.ClientEntity;
import ru.gymanager.server.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public ClientEntity createClient(ClientDto clientDto) {
        return null;
    }

    @Override
    public Optional<ClientEntity> getClient(String clientId) {
        return Optional.empty();
    }

    @Override
    public Optional<ClientEntity> getClientByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Collection<ClientEntity> getClients(String trainerId) {
        return null;
    }
}
