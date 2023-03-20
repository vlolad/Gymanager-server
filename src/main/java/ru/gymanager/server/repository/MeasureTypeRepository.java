package ru.gymanager.server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.ExerciseTypeEntity;
import ru.gymanager.server.model.MeasureTypeEntity;

public interface MeasureTypeRepository extends JpaRepository<MeasureTypeEntity, String> {
}
