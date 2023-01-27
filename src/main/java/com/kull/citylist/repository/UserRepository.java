package com.kull.citylist.repository;

import com.kull.citylist.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    User findByUsername(String name);
}
