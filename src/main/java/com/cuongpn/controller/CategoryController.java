package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CategoryRequestDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.entity.Category;
import com.cuongpn.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;
    @GetMapping("/")
    public ResponseData<List<Category>>getAllCategory(){
        return categoryService.findAll();
    }
    @PostMapping("/")
    public ResponseData<Category> addCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        return categoryService.addCategory(categoryRequestDTO);
    }
    @PutMapping("/")
    public ResponseData<Category> updateACategory(@RequestBody  CategoryRequestDTO categoryRequestDTO){
        System.out.println(categoryRequestDTO);
        return categoryService.updateCategory(categoryRequestDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseData<Category> deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }
}
