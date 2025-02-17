package com.api.apscore.dto;

import lombok.Data;

@Data
public class GenerateWorkoutRequest {
    private String level;
    private String goal;
    private int days;
}
