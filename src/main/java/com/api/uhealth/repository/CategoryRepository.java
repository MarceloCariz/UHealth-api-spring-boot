package com.api.uhealth.repository;

import com.api.uhealth.collections.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByCategoryName(String categoryName);
}
