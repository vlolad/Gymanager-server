package ru.gymanager.server.model;

import java.sql.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "gm_users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;

    @Transient
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name="login", length = 32, nullable = false, unique = true)
    private String login;

    @Column(name = "first_name", length = 32, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 32)
    private String middleName;

    @Column(name = "last_name", length = 32)
    private String lastName;

    @Column(name = "email", length = 128, unique = true)
    private String email;

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gm_users_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Collection<RoleEntity> roles = new HashSet<>();
}
