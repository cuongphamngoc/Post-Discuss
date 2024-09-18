package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDTO {
    @Email
    public String email;
    @NotBlank
    public String fullName;
    @NotBlank
    public String password;
    @NotBlank
    public String confirmPassword;



}
