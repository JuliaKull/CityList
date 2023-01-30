package com.kull.citylist.service;

import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import com.kull.citylist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CityServiceTest {

    @Mock
    private CityRepository repository;
    @InjectMocks
    private CityService service;


    @Test
    void addCityAndPhoto() {
        String name = "name";
        String url = "url";
        City city = City.builder()
                .name(name).photoUrl(url).build();

        when(repository.findByName(name)).thenReturn(null);
        when(repository.insert(city)).thenReturn(city);

        service.addCityAndPhoto(city.getName(),city.getPhotoUrl());

        verify(repository).findByName(name);
        verify(repository).insert(city);


    }

    @Test
    void updateCityAndPhoto() {
        String id = "1";
        String name = "name";
        String updatedName = "new name";
        String url = "url";
        City city = new City(id,name, url);

        when(repository.save(city)).thenReturn(city);
        when(repository.findByName(name)).thenReturn(Optional.of(city));

        City updatedCity = service.updateCityAndPhoto(name,updatedName,url);

        verify(repository).save(city);
        verify(repository).findByName(name);
        assertEquals(updatedName,updatedCity.getName());
        assertEquals(url,updatedCity.getPhotoUrl());
    }

    @Test
    void getAll() {
        int page = 0;
        int size = 12;
        List<City> cityList = List.of(City.builder().id("1").name("name1").photoUrl("url1").build(),
                City.builder().id("2").name("name2").photoUrl("url2").build());

        Page<City> expectedPage = new PageImpl<>(cityList);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());

        when(repository.findAll(pageable)).thenReturn(expectedPage);

        Page<City> actualPage = service.getAll(page, size);

        verify(repository).findAll(pageable);
        assertEquals(expectedPage, actualPage);


    }

    @Test
    void findByName() {
        String name = "Name";
        City city = City.builder()
                .id("1")
                .name("Name")
                .photoUrl("url")
                .build();
        when(repository.findByName(city.getName())).thenReturn(Optional.of(city));

        City actualCity = service.findByName(name);

        verify(repository).findByName(name);
        assertEquals(city, actualCity);

    }
}