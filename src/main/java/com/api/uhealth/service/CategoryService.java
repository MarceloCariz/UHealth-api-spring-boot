package com.api.uhealth.service;

import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import com.api.uhealth.exception.DuplicateKeyException;
import com.api.uhealth.repository.CategoryRepository;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //Inyeccion de dependencias
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Category getCategoryById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria no encontrada"));
        return category;
    }
    public boolean findByCategoryName(Category category){
        Category categoryFound = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFound == null){
            return false;
        }
        return true;
    }
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category updateCategory, Category existingCategory) {
        //No se cambia el id
        existingCategory.setCategoryName(updateCategory.getCategoryName());
        return categoryRepository.save(existingCategory);
    }
    public boolean deleteCategory(String id){
        Category isExistCategory = getCategoryById(id);
        if(isExistCategory != null){//existe
            categoryRepository.deleteById(isExistCategory.getId());
            return true;
        }
        return false;
    }

}
