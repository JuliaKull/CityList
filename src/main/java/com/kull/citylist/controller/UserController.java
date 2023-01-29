package com.kull.citylist.controller;

import com.kull.citylist.model.Role;
import com.kull.citylist.model.User;
import com.kull.citylist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
public record UserController(UserService userService) {


    @DeleteMapping("/delete")
    public void deleteRole(@PathVariable String id) {
        log.info("REST request to delete the user with id :{}", id);
        userService.delete(id);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Optional> findById(@PathVariable String id) {
        log.info("REST request to find the user with id :{}", id);
        return ResponseEntity
                .ok()
                .body(userService.findById(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<User>> findALl() {
        log.info("REST request to find all user");
        return ResponseEntity
                .ok()
                .body(userService.findAll());
    }
}
