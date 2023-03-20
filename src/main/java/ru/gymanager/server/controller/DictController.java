package ru.gymanager.server.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gymanager.server.dto.ExerciseTypeDto;
import ru.gymanager.server.dto.MeasureTypeDto;
import ru.gymanager.server.mapper.ExerciseTypeMapper;
import ru.gymanager.server.mapper.MeasureTypeMapper;
import ru.gymanager.server.service.DictService;

/**
 * Контроллер для получения значений словарей
 * Используется при создании сущностей на фронте
 */

@RestController
@RequestMapping("/dict")
public class DictController {
    private final DictService dictService;
    private final ExerciseTypeMapper exerciseTypeMapper;
    private final MeasureTypeMapper measureTypeMapper;

    @Autowired
    public DictController(
        DictService dictService,
        ExerciseTypeMapper exerciseTypeMapper,
        MeasureTypeMapper measureTypeMapper
    ) {
        this.dictService = dictService;
        this.exerciseTypeMapper = exerciseTypeMapper;
        this.measureTypeMapper = measureTypeMapper;
    }

    @GetMapping("/exercise")
    public List<ExerciseTypeDto> getExerciseTypes() {
        return exerciseTypeMapper.toDtoList(dictService.getExerciseTypes());
    }

    @GetMapping("/measure")
    public List<MeasureTypeDto> getMeasureTypes() {
        return measureTypeMapper.toDtoList(dictService.getMeasureTypes());
    }
}
