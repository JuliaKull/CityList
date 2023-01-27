package com.kull.citylist.service;

import com.kull.citylist.model.User;
import com.kull.citylist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record UserService(UserRepository repository) {

    public void save(User user) {
        repository.save(user);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public Optional findById(String id) {
        return repository.findById(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
