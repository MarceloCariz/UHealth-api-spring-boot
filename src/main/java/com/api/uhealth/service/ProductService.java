package com.api.uhealth.service;

import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import com.api.uhealth.exception.DuplicateKeyException;
import com.api.uhealth.repository.CategoryRepository;
import com.api.uhealth.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    //Inyeccion de dependencias
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Producto no encontrado"));
        return  product;
    }

    public List<Product> getAllProductsByCategoryId(String categoryId){
       Category category =  findCategoryById(categoryId);
        if (category != null) {
            List<Product> products = productRepository.getProductsByCategory(category);
            for (Product product : products) {
                product.setCategory(category);
            }
            return products;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada");
        }
    }
    public Product createProduct(Product product){
        try{
            return productRepository.save(product);
        }catch (com.mongodb.DuplicateKeyException e){
            throw new DuplicateKeyException("El valor de este atributo debe ser único.");
        }

    }
    public Product updateProduct(Product updatedProduct, Product existingProduct) {
        //Actualizar categoria
        Category newCategory = findCategoryById(updatedProduct.getCategory().getId());

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setCalories(updatedProduct.getCalories());
        existingProduct.setCarbs(updatedProduct.getCarbs());
        existingProduct.setCategory(newCategory);
        return productRepository.save(existingProduct);
    }

    public boolean deleteProduct(String id){
        Product isExistProduct = getProductById(id);
        if(isExistProduct != null){//existe
            productRepository.deleteById(isExistProduct.getId());
            return true;
        }
        return false;
    }

    public Category findCategoryById(String categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria no encontrado"));
        return  category;
    }
    public boolean findByProductName(Product product){
        Product isProduct = productRepository.findByProductName(product.getProductName());
        if(isProduct != null){ //existe
            return true;
        }
        return false;
    }
}
