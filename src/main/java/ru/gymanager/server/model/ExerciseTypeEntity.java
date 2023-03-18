package ru.gymanager.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "gm_dict_exercises")
public class ExerciseTypeEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;
    @Column(name = "system_name")
    private String systemName;
    @Column(name = "caption")
    private String caption;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "gm_dict_measures",
    joinColumns = {@JoinColumn(name = "id")})
    private MeasureTypeEntity measure;
}
