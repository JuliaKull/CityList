package com.kull.citylist.service;

import com.kull.citylist.dto.CityDto;
import com.kull.citylist.exception.CityException;
import com.kull.citylist.exception.UserException;
import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import com.kull.citylist.service.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper mapper;
    public final static String MESSAGE = "City not found";


    public CityDto addCityAndPhoto(CityDto cityDto) {
        City city = mapper.toEntity(cityDto);
        cityRepository.save(city);
        return mapper.toDto(city);
    }

    public CityDto updateCityAndPhoto(String name, CityDto cityDto) {
        City city = cityRepository.findByName(name).orElseThrow(() -> new CityException(MESSAGE));
        mapper.update(city, cityDto);
        cityRepository.save(city);
        return mapper.toDto(city);
    }

    public Page<CityDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        Page<City> cities = cityRepository.findAll(pageable);
        return cities.map(city -> mapper.toDto(city));}


    public CityDto findByName(CityDto cityDto) {
        City city = cityRepository.findByName(cityDto.getName()).orElseThrow(() -> new CityException(MESSAGE));
        return mapper.toDto(city);
    }
}
