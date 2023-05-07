package com.api.uhealth.repository;

import com.api.uhealth.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByEmail(String email);
    Optional<User> findOneByEmail(String email);
}
