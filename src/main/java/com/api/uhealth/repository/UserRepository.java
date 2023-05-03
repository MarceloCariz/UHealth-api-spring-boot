package com.api.uhealth.repository;

import com.api.uhealth.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByEmail(String email);
}
