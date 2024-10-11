package com.cuongpn.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;

public class ArticleRequestByTagDTO {
    @NotBlank
    String tag;
}
