package com.kull.citylist.controller;

import com.kull.citylist.model.Role;
import com.kull.citylist.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/roles")
@Slf4j
public record RoleController(RoleService roleService) {

    @PostMapping("/add")
    public void addRole(@RequestBody Role role) {
        log.info("REST request to add new role :{}", role);
        roleService.save(role);
    }

    @DeleteMapping("/delete")
    public void deleteRole(@PathVariable String id) {
        log.info("REST request to delete the role with id :{}", id);
        roleService.delete(id);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Optional> findById(@PathVariable String id) {
        log.info("REST request to find the role with id :{}", id);
        return ResponseEntity
                .ok()
                .body(roleService.findById(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Role>> findALl() {
        log.info("REST request to find all roles");
        return ResponseEntity
                .ok()
                .body(roleService.findAll());
    }

    @RequestMapping("/assign/{userId}/{roleId}")
    public void assignRole(@PathVariable String userId,@PathVariable String roleId){
        roleService.assignUserRole(roleId,userId);
    }




}
