package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CategoryRequestDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.entity.Category;

import java.util.List;

public interface CategoryService {

    ResponseData<List<Category>> findAll();
    ResponseData<Category> addCategory(CategoryRequestDTO categoryRequestDTO);

    ResponseData<Category> deleteCategory(Integer id);

    ResponseData<Category> updateCategory(CategoryRequestDTO category);
}
