package com.cuongpn.dto.requestDTO;

import com.cuongpn.validator.PasswordMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch
public class ResetPasswordDTO {
    String token;

    String newPassword;

    String confirmPassword;
}
