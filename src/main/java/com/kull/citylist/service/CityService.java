package com.kull.citylist.service;

import com.kull.citylist.exception.CityException;
import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public record CityService(CityRepository cityRepository,MongoTemplate mongoTemplate) {

    private final static String MESSAGE = "City not found";


    public String addCityAndPhoto(String name, MultipartFile file) throws IOException {
        City city = new City();
        city.setName(name);
        city.setPhoto(file.getBytes());
        city = cityRepository.insert(city);
        return city.getId();
    }

    public City updateCityAndPhoto(String name, String updatedName, MultipartFile image) throws IOException {
        City city = cityRepository.findByName(name).orElseThrow(() -> new CityException(MESSAGE));
        if (!name.equals(updatedName)){
            city.setName(updatedName);}
        city.setPhoto(image.getBytes());
        return cityRepository.save(city);
    }


    public Page<City> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        return cityRepository.findAll(pageable);
    }


    public City findByName(String name) {
        return cityRepository.findByName(name).orElseThrow(() -> new CityException(MESSAGE));
    }
}
