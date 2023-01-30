package com.kull.citylist.service;

import com.kull.citylist.dto.CityDto;
import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import com.kull.citylist.service.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final CityRepository repository;
    private final CityMapper mapper;


    public void saveCsvFile(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser parser = new CSVParser(reader, CSVFormat.Builder.create().setHeader("id", "name", "photo").build());
            List<CityDto> cityList = parser.getRecords().parallelStream().map(record -> {
                CityDto city = new CityDto();
                city.setId(record.get("id"));
                city.setName(record.get("name"));
                city.setPhotoUrl(record.get("photo"));
                return city;
            }).collect(Collectors.toList());
            repository.saveAll(mapper.toEntities(cityList));
        }
    }
}
