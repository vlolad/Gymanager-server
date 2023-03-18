package ru.gymanager.server.model;

import java.sql.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "gm_clients")
@Getter
@Setter
@NoArgsConstructor
public class ClientEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "first_Name", length = 32, nullable = false)
    private String firstName;

    @Column(name = "middle_Name", length = 32)
    private String middleName;

    @Column(name = "last_Name", length = 32)
    private String lastName;

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;

    @Column(name = "description", length = 1024)
    private String description;

    @Transient
    private List<WorkoutEntity> workouts;
}
