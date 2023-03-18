package ru.gymanager.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "gm_dict_measures")
@Getter
@Setter
@NoArgsConstructor
public class MeasureTypeEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;
    @Column(name = "system_name")
    private String systemName;
    @Column(name = "caption")
    private String caption;
    @Column(name = "units")
    private String units;
}
