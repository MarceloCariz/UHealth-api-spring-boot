package com.api.uhealth.repository;

import com.api.uhealth.classes.UserRequestUpdate;
import com.api.uhealth.collections.User;
import com.api.uhealth.interfaces.UserGet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {


    List<UserGet> findAllBy();

//    UserRequestUpdate updateUser(String userId, UserRequestUpdate user);
    User findUserByEmail(String email);
    Optional<User> findOneByEmail(String email);
}
