package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email
    public String email;
    @NotBlank
    public String fullname;
    @NotBlank
    public String password;
    @NotBlank
    public String confirmPassword;



}
