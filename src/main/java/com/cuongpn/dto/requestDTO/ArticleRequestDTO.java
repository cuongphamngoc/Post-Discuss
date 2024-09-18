package com.cuongpn.dto.requestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class ArticleRequestDTO {
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    @NotEmpty
    private Set<String> tags;
    @NotBlank
    private String title;

}
