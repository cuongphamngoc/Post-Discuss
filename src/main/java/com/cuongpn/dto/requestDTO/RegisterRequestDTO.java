package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDTO {
    @Email
    private String email;
    @NotBlank
    private String fullName;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;



}
