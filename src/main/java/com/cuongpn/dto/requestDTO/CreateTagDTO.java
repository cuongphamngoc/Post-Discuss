package com.cuongpn.dto.requestDTO;

import com.cuongpn.enums.TagType;
import com.cuongpn.validator.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTagDTO {
    @NotBlank
    private String name;
    @NotNull
    @EnumValue(name="Tag Type", enumClass = TagType.class)
    private String tagType;
}
