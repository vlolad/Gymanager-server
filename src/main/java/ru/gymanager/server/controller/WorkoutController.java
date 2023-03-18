package ru.gymanager.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.dto.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @GetMapping("/{workoutId}")
    public WorkoutDto getWorkout(@PathVariable String workoutId) {
        //TODO заменить мок на функционал
        MeasureTypeDto measure = new MeasureTypeDto("001", "count", "count", "times");
        ExerciseTypeDto exerciseType = new ExerciseTypeDto("002", "pushup", "push-ups",
                "simple exercise", measure);
        List<ExerciseDto> exercises = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ExerciseDto exercise = new ExerciseDto("004" + i, "firstTypeExercise", exerciseType);
            ExerciseResultDto result1 = new ExerciseResultDto("004" + i + "1", 1, 10);
            ExerciseResultDto result2 = new ExerciseResultDto("004" + i + "2", 2, 9);
            exercise.setResults(List.of(result1, result2));
            exercises.add(exercise);
        }
        WorkoutDto workout = WorkoutDto.builder()
                .id(workoutId)
                .startDate(LocalDateTime.now().plusHours(1))
                .description("Train # 1")
                .exercises(exercises)
                .build();
        return workout;
    }
}
