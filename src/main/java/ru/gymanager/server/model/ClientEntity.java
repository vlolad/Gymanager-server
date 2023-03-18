package ru.gymanager.server.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column(name = "firstName", length = 32, nullable = false)
    private String firstName;

    @Column(name = "middleName", length = 32)
    private String middleName;

    @Column(name = "lastName", length = 32)
    private String lastName;

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;

    @Column(name = "description", length = 1024)
    private String description;
}
