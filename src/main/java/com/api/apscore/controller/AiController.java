package com.api.apscore.controller;

import com.api.apscore.dto.GenerateWorkoutRequest;
import com.api.apscore.service.AiClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI Workout Generator", description = "Endpoints para gerar planos de treino usando IA (GPT-4)")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiClientService aiClientService;

    @Operation(
            summary = "Gera um plano de treino",
            description = "Gera um plano de treino com base no nível, objetivo e dias disponíveis usando um modelo de IA (GPT-4)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna o plano de treino gerado",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400",
                    description = "Parâmetros inválidos ou erro de validação",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Erro interno ao gerar plano",
                    content = @Content)
    })
    @PostMapping("/generate-workout")
    public ResponseEntity<String> generateWorkout(@RequestBody GenerateWorkoutRequest request) {
        String prompt = String.format(
                "Crie um plano de treino para um(a) %s que tem como objetivo %s, " +
                        "podendo treinar %d dias na semana. Explique cada dia em detalhes e seja conciso.",
                request.getLevel(), request.getGoal(), request.getDays()
        );

        String workoutPlan = aiClientService.generateWorkoutPlan(prompt);

        return ResponseEntity.ok(workoutPlan);
    }
}
