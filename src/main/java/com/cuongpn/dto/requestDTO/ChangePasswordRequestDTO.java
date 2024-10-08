package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ChangePasswordRequestDTO {
    @NotBlank
    String oldPassword;
    @NotBlank
    String newPassword;
    @NotBlank
    String confirmPassword;
}
