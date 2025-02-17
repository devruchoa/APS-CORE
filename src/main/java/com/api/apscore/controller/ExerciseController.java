package com.api.apscore.controller;

import com.api.apscore.dto.ExerciseDTO;
import com.api.apscore.model.Exercise;
import com.api.apscore.service.ExerciseService;
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

@Tag(name = "Exercise", description = "Endpoints para gerenciamento de exercícios de musculação")
@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Operation(summary = "Cria um novo Exercise",
            description = "Cria um novo Exercise e retorna o objeto persistido no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exercise criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro de validação",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseService.createExercise(exerciseDTO);
        return ResponseEntity.status(201).body(exercise);
    }

    @Operation(summary = "Lista todos os Exercises",
            description = "Retorna uma lista com todos os Exercises do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Exercises retornada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)))
    })
    @GetMapping
    public ResponseEntity<List<Exercise>> listAllExercises() {
        List<Exercise> exercises = exerciseService.listAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @Operation(summary = "Obtém um Exercise pelo ID",
            description = "Retorna o Exercise correspondente ao ID informado, se existir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise retornado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "404", description = "Exercise não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Atualiza um Exercise existente",
            description = "Atualiza as informações de um Exercise com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "404", description = "Exercise não encontrado para atualização",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) {
        Exercise updated = exerciseService.updateExercise(id, exerciseDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deleta um Exercise",
            description = "Remove o Exercise correspondente ao ID informado, se existir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exercise deletado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Exercise não encontrado para exclusão",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        boolean deleted = exerciseService.deleteExercise(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
