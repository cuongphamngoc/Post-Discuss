package com.cuongpn.dto.requestDTO;

import com.cuongpn.dto.validator.PasswordMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch
public class ResetPasswordRequestDTO {
    String token;

    String newPassword;

    String confirmPassword;
}
