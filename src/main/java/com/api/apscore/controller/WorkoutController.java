package com.api.apscore.controller;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.dto.WorkoutDTO;
import com.api.apscore.model.Exercise;
import com.api.apscore.model.Workout;
import com.api.apscore.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Workout", description = "Endpoints para gerenciamento de treinos de musculação")
@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @Operation(summary = "Cria um novo Workout",
            description = "Cria um novo Workout e retorna o objeto persistido no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro de validação",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        Workout workout = workoutService.createWorkout(workoutDTO);
        return ResponseEntity.status(201).body(workout);
    }

    @Operation(summary = "Lista todos os Workouts",
            description = "Retorna uma lista com todos os Workouts do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Workouts retornada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)))
    })
    @GetMapping
    public ResponseEntity<List<Workout>> listAllWorkouts() {
        List<Workout> workouts = workoutService.listAllWorkouts();
        return ResponseEntity.ok(workouts);
    }

    @Operation(summary = "Obtém um Workout pelo ID",
            description = "Retorna o Workout correspondente ao ID informado, se existir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout retornado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class))),
            @ApiResponse(responseCode = "404", description = "Workout não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return workoutService.getWorkoutById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Atualiza um Workout existente",
            description = "Atualiza as informações de um Workout com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class))),
            @ApiResponse(responseCode = "404", description = "Workout não encontrado para atualização",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody WorkoutDTO workoutDTO) {
        Workout updated = workoutService.updateWorkout(id, workoutDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deleta um Workout",
            description = "Remove o Workout correspondente ao ID informado, se existir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workout deletado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Workout não encontrado para exclusão",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        boolean deleted = workoutService.deleteWorkout(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um Exercise a um Workout",
            description = "Cria e associa um novo Exercise ao Workout especificado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exercise adicionado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "404", description = "Workout não encontrado para adicionar Exercise",
                    content = @Content)
    })
    @PostMapping("/{workoutId}/exercises")
    public ResponseEntity<Exercise> addExerciseToWorkout(
            @PathVariable Long workoutId,
            @RequestBody ExerciseDTO exerciseDTO
    ) {
        Exercise exercise = workoutService.addExerciseToWorkout(workoutId, exerciseDTO);
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).body(exercise);
    }
}
