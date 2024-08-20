package com.cuongpn.dto.requestDTO;

import com.cuongpn.dto.validator.PasswordMatch;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch
public class ResetPasswordRequestDto {
    String token;

    String newPassword;

    String confirmPassword;
}
