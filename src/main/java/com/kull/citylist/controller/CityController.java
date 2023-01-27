package com.kull.citylist.controller;

import com.kull.citylist.model.City;
import com.kull.citylist.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/cities")
@Slf4j
public record CityController(CityService cityService) {

    @PostMapping("/add")
    public ResponseEntity<String> addCityAndPhoto(@RequestParam("name") String name,
                               @RequestParam("image") MultipartFile image) throws IOException {
        log.info("REST request to add new city :{}", name);
        return ResponseEntity.ok().body(cityService.addCityAndPhoto(name, image));
    }

    @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
    @PutMapping("/update/{name}")
    public ResponseEntity<City> updateCityAndPhoto(@RequestParam("name") String name, @RequestParam("updated_name") String updatedName,
                                                   @RequestParam("image") MultipartFile image) throws IOException {
        log.info("REST request to add new city :{}", name);
        return ResponseEntity.ok().body(cityService.updateCityAndPhoto(name,updatedName,image));
    }

    @GetMapping()
    public ResponseEntity<Page<City>> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "12") int size)  {
        log.info("REST request to get cities");
        return ResponseEntity.ok().body(cityService.getAll(page,size));
    }

    @GetMapping("/{name}")
    public ResponseEntity<City> findByName(@PathVariable("name") String name) {
        log.info("REST request to get city :{}", name);
        return ResponseEntity.ok().body(cityService.findByName(name));
    }

    }

