package com.api.uhealth.service;

import com.api.uhealth.collections.Product;
import com.api.uhealth.exception.BadRequestException;
import com.api.uhealth.repository.ProductRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;


    //Inyeccion de dependencias
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){
        try{
            return productRepository.save(product);
        }catch (DuplicateKeyException e){
            throw new BadRequestException("El valor de este atributo debe ser único.");
        }

    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow();
        return  product;
    }
}
