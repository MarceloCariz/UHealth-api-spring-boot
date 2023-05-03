package com.api.uhealth.controller;


import com.api.uhealth.collections.Category;
import com.api.uhealth.exception.BadRequestException;
import com.api.uhealth.service.CategoryService;
import com.mongodb.DuplicateKeyException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result){
        //Validar si hay un campo con algun error
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        Category categorySaved = categoryService.createCategory(category);
        return new ResponseEntity<Category>(categorySaved, HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }




}
