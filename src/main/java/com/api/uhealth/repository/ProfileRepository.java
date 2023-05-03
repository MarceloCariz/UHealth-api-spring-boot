package com.api.uhealth.repository;

import com.api.uhealth.collections.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
}
