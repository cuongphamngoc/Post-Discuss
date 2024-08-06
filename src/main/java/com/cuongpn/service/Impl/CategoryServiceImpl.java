package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CategoryRequestDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.entity.Category;
import com.cuongpn.mapper.CategoryMapper;
import com.cuongpn.repository.CategoryRepo;
import com.cuongpn.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepo categoryRepo;

    private CategoryMapper categoryMapper;
    @Override
    public ResponseData<List<Category>> findAll() {
        List<Category> list = categoryRepo.findAll();
        return new ResponseData<>(HttpStatus.OK.value(), "Success",list);

    }


    @Override
    public ResponseData<Category> addCategory(CategoryRequestDTO categoryRequest) {
        Category category = categoryMapper.categoryRequestDTOToCategory(categoryRequest);
        System.out.println(categoryRequest+" "+ category);
        Category res = categoryRepo.save(category);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Category insert success",category);
    }

    @Override
    public ResponseData<Category> deleteCategory(Integer id) {
        Category category = categoryRepo.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Category id not found "+id));
        categoryRepo.delete(category);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Category delete success",category);
    }

    @Override
    public ResponseData<Category> updateCategory(CategoryRequestDTO categoryRequest) {
        Category category = categoryRepo.findById(categoryRequest.getCategoryId()).orElseThrow(
                ()-> new IllegalArgumentException("Category id not found "+categoryRequest.getCategoryId()));
        category.setCategoryTitle(categoryRequest.getCategoryTitle());
        category.setCategoryDescription(categoryRequest.getCategoryDescription());
        Category res = categoryRepo.save(category);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Category update success",res);
    }
}
