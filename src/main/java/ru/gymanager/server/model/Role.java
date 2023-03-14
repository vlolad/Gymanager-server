package ru.gymanager.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "_roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    // TODO enum default roles

    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.IdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
