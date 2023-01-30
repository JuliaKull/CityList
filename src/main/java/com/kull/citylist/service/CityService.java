package com.kull.citylist.service;

import com.kull.citylist.exception.CityException;
import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public record CityService(CityRepository cityRepository) {

    public final static String MESSAGE = "City not found";
    private final static String SECUTITY_MESSAGE = "Access denied, lack of rights";


    public void addCityAndPhoto(String name, String link) {
        City city = new City();
        city.setName(name);
        city.setPhotoUrl(link);
        if(cityRepository.findByName(name)==null){
        city = cityRepository.insert(city);}
    }

    public City updateCityAndPhoto(String name, String updatedName, String link) {
        City city = cityRepository.findByName(name).orElseThrow(() -> new CityException(SECUTITY_MESSAGE));
        if (!name.equals(updatedName)) {
            city.setName(updatedName);
        }
        city.setPhotoUrl(link);
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
