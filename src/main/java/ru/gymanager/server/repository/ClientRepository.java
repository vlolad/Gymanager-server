package ru.gymanager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findByPhone(String phone);
    List<ClientEntity> findAllByIdIn(List<String> clientsId);
}
