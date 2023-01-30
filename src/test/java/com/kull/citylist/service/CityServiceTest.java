package com.kull.citylist.service;

import com.kull.citylist.dto.CityDto;
import com.kull.citylist.exception.UserException;
import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import com.kull.citylist.repository.UserRepository;
import com.kull.citylist.service.mapper.CityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.kull.citylist.service.CityService.MESSAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CityServiceTest {

    @Mock
    private CityRepository repository;
    @InjectMocks
    private CityService service;
    @Spy
    private CityMapper mapper;


    @Test
    void addCityAndPhoto() {
            CityDto cityDto = new CityDto();
            City city = new City();
            when(mapper.toEntity(cityDto)).thenReturn(city);
            when(repository.save(any(City.class))).thenReturn(city);
            when(mapper.toDto(city)).thenReturn(cityDto);

            CityDto result = service.addCityAndPhoto(cityDto);

            assertEquals(cityDto, result);
        }

    @Test
    void updateCityAndPhoto() {
        String name = "name";
        CityDto cityDto = CityDto.builder().id("1").name("new name").photoUrl("url").build();
        City city = City.builder().id("1").name("name").photoUrl("url").build();

        when(repository.findByName(name)).thenReturn(Optional.of(city));

        when(mapper.toDto(city)).thenReturn(cityDto);

        CityDto result = service.updateCityAndPhoto(name, cityDto);

        assertEquals(cityDto, result);
    }


    @Test
    void getAll() {
        int page = 0;
        int size = 12;
        List<CityDto> cityListDto = Arrays.asList(CityDto.builder().id("1").name("name1").photoUrl("url1").build(),
                CityDto.builder().id("2").name("name2").photoUrl("url2").build());
        List<City> cityList = Arrays.asList(City.builder().id("1").name("name1").photoUrl("url1").build(),
                City.builder().id("2").name("name2").photoUrl("url2").build());

        Page<City> cities = new PageImpl<>(cityList);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        when(repository.findAll(pageable)).thenReturn(cities);
        when(mapper.toDtos(cityList)).thenReturn(cityListDto);


        Page<CityDto> result = service.getAll(page, size);

        assertEquals(2, result.getTotalElements());

    }
//        Page<City> expectedPage = new PageImpl<>(cityList);
//        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
//
//        when(repository.findAll(pageable)).thenReturn(expectedPage);
//
//        Page<CityDto> actualPage = service.getAll(page, size);
//
//        verify(repository).findAll(pageable);
//        assertEquals(expectedPage, actualPage);



    @Test
    void findByName() {

        CityDto cityDto = new CityDto();
        cityDto.setName("name");
        City city = new City();
        when(repository.findByName(cityDto.getName())).thenReturn(Optional.of(city));
        when(mapper.toDto(city)).thenReturn(cityDto);

        CityDto result = service.findByName(cityDto);

        assertEquals(cityDto, result);
    }
}