package com.cuongpn.mapper;

import com.cuongpn.dto.requestDTO.CategoryRequestDTO;
import com.cuongpn.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface CategoryMapper {
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "categoryTitle",target = "categoryTitle")
    @Mapping(source = "categoryDescription", target = "categoryDescription")
    Category categoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryRequestDTO categoryToCategoryRequestDTO(Category category);
}
