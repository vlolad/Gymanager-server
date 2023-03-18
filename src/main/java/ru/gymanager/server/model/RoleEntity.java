package ru.gymanager.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "gm_roles")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;

    @Column(name = "name", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleEntity(String name) {
        this.role = Role.valueOf(name);
    }

    public enum Role {
        ADMIN,
        USER
    }
}
