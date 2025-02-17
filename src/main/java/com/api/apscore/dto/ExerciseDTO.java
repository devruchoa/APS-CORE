package com.api.apscore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExerciseDTO {
    private String name;
    private String targetMuscle;
    private Integer sets;
    private Integer reps;
    private BigDecimal suggestedWeight;
}
