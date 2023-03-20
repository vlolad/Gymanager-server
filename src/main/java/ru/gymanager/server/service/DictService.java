package ru.gymanager.server.service;

import java.util.List;
import ru.gymanager.server.model.ExerciseTypeEntity;
import ru.gymanager.server.model.MeasureTypeEntity;

public interface DictService {
    List<ExerciseTypeEntity> getExerciseTypes();
    List<MeasureTypeEntity> getMeasureTypes();
}
