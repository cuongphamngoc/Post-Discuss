package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequest {
    @NotBlank(message = "User name can't blank")
    String username;

    @NotBlank(message =  "password can't blank")
    String password;
}
