package ru.gymanager.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gm_workouts")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "description")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="gm_workout_exercises",
            joinColumns = {@JoinColumn(name = "workout_id", referencedColumnName = "id")})
    private List<ExerciseEntity> exercises;
}
