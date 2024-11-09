package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSeriesDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotEmpty
    private List<String> tags;

    private List<Long> articles;
}
