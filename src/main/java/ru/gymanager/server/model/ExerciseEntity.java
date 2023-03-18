package ru.gymanager.server.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gm_workout_exercises")
@Getter
@Setter
public class ExerciseEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;
    @Column(name = "note")
    private String note;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "gm_dict_exercises",
            joinColumns = {@JoinColumn(name = "id")})
    private ExerciseTypeEntity exerciseType;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gm_exercise_results",
    joinColumns = {@JoinColumn(name = "exercise_id")})
    private List<ExerciseResultEntity> exerciseResult;
}
