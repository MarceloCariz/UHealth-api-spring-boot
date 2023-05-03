package com.api.uhealth.repository;

import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductName(String productName);

    List<Product> getProductsByCategory(Category category);

}
