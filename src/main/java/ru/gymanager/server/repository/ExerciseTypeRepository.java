package ru.gymanager.server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymanager.server.model.ExerciseTypeEntity;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseTypeEntity, String> {
}
