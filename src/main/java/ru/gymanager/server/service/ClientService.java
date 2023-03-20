package ru.gymanager.server.service;

import java.util.List;
import ru.gymanager.server.dto.ClientDto;
import ru.gymanager.server.model.ClientEntity;

public interface ClientService {
    ClientEntity createClient(ClientDto clientDto);

    ClientEntity updateClient(ClientDto clientDto);
    ClientEntity getClient(String clientId);
    ClientEntity getClientByPhone(String phone);
    List<ClientEntity> getClients();
    ClientEntity createLink(ClientEntity client);
    void removeLink(String clientId);
}
