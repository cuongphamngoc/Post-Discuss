package com.cuongpn.dto.requestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String imageUrl;
    @NotEmpty
    private List<String> tags;

    private Long series;
}
