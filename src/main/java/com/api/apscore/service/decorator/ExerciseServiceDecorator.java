package com.api.apscore.service.decorator;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.model.Exercise;
import com.api.apscore.service.ExerciseService;

import java.util.List;
import java.util.Optional;

public abstract class ExerciseServiceDecorator extends ExerciseService {
    protected final ExerciseService exerciseService;

    protected ExerciseServiceDecorator(ExerciseService exerciseService) {
        super(exerciseService.getExerciseRepository());
        this.exerciseService = exerciseService;
    }

    @Override
    public Exercise createExercise(ExerciseDTO exerciseDTO) {
        return exerciseService.createExercise(exerciseDTO);
    }

    @Override
    public List<Exercise> listAllExercises() {
        return exerciseService.listAllExercises();
    }

    @Override
    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseService.getExerciseById(id);
    }

    @Override
    public Exercise updateExercise(Long id, ExerciseDTO exerciseDTO) {
        return exerciseService.updateExercise(id, exerciseDTO);
    }

    @Override
    public boolean deleteExercise(Long id) {
        return exerciseService.deleteExercise(id);
    }
}
