package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequestDTO {
    @Email(message =    "${Email.message}")
    String email;

    @NotBlank(message =  "password can't blank")
    String password;
}
