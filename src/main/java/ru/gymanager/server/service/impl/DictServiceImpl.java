package ru.gymanager.server.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gymanager.server.model.ExerciseTypeEntity;
import ru.gymanager.server.model.MeasureTypeEntity;
import ru.gymanager.server.repository.ExerciseTypeRepository;
import ru.gymanager.server.repository.MeasureTypeRepository;
import ru.gymanager.server.service.DictService;

@Service
@Slf4j
public class DictServiceImpl implements DictService {
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final MeasureTypeRepository measureTypeRepository;

    @Autowired
    public DictServiceImpl(ExerciseTypeRepository exerciseTypeRepository, MeasureTypeRepository measureTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.measureTypeRepository = measureTypeRepository;
    }

    //maybe cacheable!

    @Override
    public List<ExerciseTypeEntity> getExerciseTypes() {
        return exerciseTypeRepository.findAll();
    }

    @Override
    public List<MeasureTypeEntity> getMeasureTypes() {
        return measureTypeRepository.findAll();
    }
}
