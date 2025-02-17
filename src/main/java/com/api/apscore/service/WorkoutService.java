package com.api.apscore.service;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.dto.WorkoutDTO;
import com.api.apscore.model.Exercise;
import com.api.apscore.model.Workout;
import com.api.apscore.repository.ExerciseRepository;
import com.api.apscore.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public Workout createWorkout(WorkoutDTO workoutDTO) {
        Workout workout = Workout.builder()
                .name(workoutDTO.getName())
                .goal(workoutDTO.getGoal())
                .startDate(workoutDTO.getStartDate())
                .build();

        if (workoutDTO.getExercises() != null && !workoutDTO.getExercises().isEmpty()) {
            List<Exercise> exerciseList = new ArrayList<>();
            for (ExerciseDTO dto : workoutDTO.getExercises()) {
                Exercise exercise = Exercise.builder()
                        .name(dto.getName())
                        .targetMuscle(dto.getTargetMuscle())
                        .sets(dto.getSets())
                        .reps(dto.getReps())
                        .suggestedWeight(dto.getSuggestedWeight())
                        .workout(workout)
                        .build();
                exerciseList.add(exercise);
            }
            workout.setExercises(exerciseList);
        }

        return workoutRepository.save(workout);
    }

    public List<Workout> listAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    public Workout updateWorkout(Long id, WorkoutDTO workoutDTO) {
        return workoutRepository.findById(id).map(workout -> {
            workout.setName(workoutDTO.getName());
            workout.setGoal(workoutDTO.getGoal());
            workout.setStartDate(workoutDTO.getStartDate());
            return workoutRepository.save(workout);
        }).orElse(null);
    }

    public boolean deleteWorkout(Long id) {
        return workoutRepository.findById(id).map(workout -> {
            workoutRepository.delete(workout);
            return true;
        }).orElse(false);
    }

    public Exercise addExerciseToWorkout(Long workoutId, ExerciseDTO exerciseDTO) {
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
        if (optionalWorkout.isEmpty()) {
            return null;
        }

        Workout workout = optionalWorkout.get();
        Exercise exercise = Exercise.builder()
                .name(exerciseDTO.getName())
                .targetMuscle(exerciseDTO.getTargetMuscle())
                .sets(exerciseDTO.getSets())
                .reps(exerciseDTO.getReps())
                .suggestedWeight(exerciseDTO.getSuggestedWeight())
                .workout(workout)
                .build();

        return exerciseRepository.save(exercise);
    }
}
