package com.api.uhealth.repository;

import com.api.uhealth.collections.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
