package com.api.apscore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "O username não pode ser nulo ou vazio")
    private String username;

    @NotBlank(message = "A senha não pode ser nula ou vazia")
    private String password;
}
