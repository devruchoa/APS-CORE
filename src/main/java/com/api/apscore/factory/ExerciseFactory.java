package com.api.apscore.factory;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.model.Exercise;

public class ExerciseFactory {
    public static Exercise createExercise(ExerciseDTO exerciseDTO) {
        return Exercise.builder()
                .name(exerciseDTO.getName())
                .targetMuscle(exerciseDTO.getTargetMuscle())
                .sets(exerciseDTO.getSets())
                .reps(exerciseDTO.getReps())
                .suggestedWeight(exerciseDTO.getSuggestedWeight())
                .build();
    }
}
