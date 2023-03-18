package ru.gymanager.server.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//Сущность с информацией о подходе
@Entity
@Table(name = "gm_exercise_results")
@Getter
@Setter
public class ExerciseResultEntity {
    @Id
    @GenericGenerator(name = "entity_id", strategy = "ru.gymanager.server.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity_id")
    @Column(name = "id")
    private String id;
    @Column(name = "execution_order")
    private Integer executionOrder;
    @Column(name = "result")
    private Integer result;
}
