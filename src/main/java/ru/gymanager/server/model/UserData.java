package ru.gymanager.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "_users")
@Getter
@Setter
@NoArgsConstructor
public class UserData {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.IdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name="login", length = 32, nullable = false, unique = true)
    private String login;

    @Column(name = "email", length = 128, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 128, nullable = false)
    private String password;
}
