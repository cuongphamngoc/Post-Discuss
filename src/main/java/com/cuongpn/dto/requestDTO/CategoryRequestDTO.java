package com.cuongpn.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    Integer categoryId;
    String categoryTitle;
    String categoryDescription;
}
