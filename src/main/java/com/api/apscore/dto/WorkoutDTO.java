package com.api.apscore.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutDTO {
    private String name;
    private String goal;
    private LocalDate startDate;
    private List<ExerciseDTO> exercises;
}
