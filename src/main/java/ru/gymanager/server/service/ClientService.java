package ru.gymanager.server.service;

import java.util.Collection;
import java.util.Optional;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.model.ClientEntity;

public interface ClientService {
    ClientEntity createClient(ClientDto clientDto);
    Optional<ClientEntity> getClient(String clientId);
    Optional<ClientEntity> getClientByPhone(String phone);
    Collection<ClientEntity> getClients(String trainerId);
}
