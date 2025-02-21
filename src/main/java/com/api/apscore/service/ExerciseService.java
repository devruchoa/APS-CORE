package com.api.apscore.service;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.factory.ExerciseFactory;
import com.api.apscore.model.Exercise;
import com.api.apscore.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public Exercise createExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = ExerciseFactory.createExercise(exerciseDTO);
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> listAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise updateExercise(Long id, ExerciseDTO exerciseDTO) {
        return exerciseRepository.findById(id).map(exercise -> {
            exercise.setName(exerciseDTO.getName());
            exercise.setTargetMuscle(exerciseDTO.getTargetMuscle());
            exercise.setSets(exerciseDTO.getSets());
            exercise.setReps(exerciseDTO.getReps());
            exercise.setSuggestedWeight(exerciseDTO.getSuggestedWeight());
            return exerciseRepository.save(exercise);
        }).orElse(null);
    }

    public boolean deleteExercise(Long id) {
        return exerciseRepository.findById(id).map(exercise -> {
            exerciseRepository.delete(exercise);
            return true;
        }).orElse(false);
    }

    public ExerciseRepository getExerciseRepository() {
        return exerciseRepository;
    }
}
