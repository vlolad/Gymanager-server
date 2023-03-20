package ru.gymanager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.ClientTrainerLink;

import java.util.List;

public interface ClientTrainerLinkRepository extends JpaRepository<ClientTrainerLink, String> {
    List<ClientTrainerLink> findAllByTrainerId(String trainerId);
    ClientTrainerLink findByClientIdAndTrainerId(String clientId, String trainerId);
}
