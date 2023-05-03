package com.api.uhealth.controller;


import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import com.api.uhealth.exception.DuplicateKeyException;
import com.api.uhealth.service.CategoryService;
import com.mongodb.MongoWriteException;
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



    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id){
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }


    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result){
        //Validar si hay un campo con algun error
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        if(categoryService.findByCategoryName(category)){
            return new ResponseEntity<>("La categoria ya existe", HttpStatus.BAD_REQUEST);
        }

        Category categorySaved = categoryService.createCategory(category);
        return new ResponseEntity<Category>(categorySaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @RequestBody Category updatedCategory, BindingResult result) {
        if(result.hasErrors()) {
            // manejar errores de validación
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        //Validar si existe la categoria
        Category isCategory = categoryService.getCategoryById(id);
        if(isCategory == null){
            return new ResponseEntity<>("La categoria no existe", HttpStatus.BAD_REQUEST);
        }
        //Categoria con nueva info, producto directamente desde la bd
        Category categoryUpdated = categoryService.updateCategory( updatedCategory, isCategory);
        return new ResponseEntity<Category>(categoryUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Categoria eliminada correctamente", HttpStatus.CREATED);
    }



}
