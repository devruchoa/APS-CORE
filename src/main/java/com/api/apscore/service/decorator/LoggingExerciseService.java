package com.api.apscore.service.decorator;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.model.Exercise;
import com.api.apscore.service.ExerciseService;

public class LoggingExerciseService extends ExerciseServiceDecorator {

    public LoggingExerciseService(ExerciseService exerciseService) {
        super(exerciseService);
    }

    @Override
    public Exercise createExercise(ExerciseDTO exerciseDTO) {
        System.out.println("Criando exercício: " + exerciseDTO.getName());
        return super.createExercise(exerciseDTO);
    }

    @Override
    public boolean deleteExercise(Long id) {
        System.out.println("Deletando exercício com ID: " + id);
        return super.deleteExercise(id);
    }
}
