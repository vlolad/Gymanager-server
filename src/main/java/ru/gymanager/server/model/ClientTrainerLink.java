package ru.gymanager.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gm_trainer_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientTrainerLink {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "trainer_user_id")
    private String trainerId;
    @Column(name = "client_id")
    private String clientId;
}
