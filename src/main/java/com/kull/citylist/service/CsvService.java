package com.kull.citylist.service;

import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public record CsvService (CityRepository repository) {

    public void saveCsvFile(MultipartFile file) throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            CSVParser parser = new CSVParser(reader,CSVFormat.Builder.create().setHeader("id","name","photo").build());
            List<City> cityList = parser.getRecords().parallelStream().map(record ->{
                City city = new City();
                city.setId(record.get("id"));
                city.setName(record.get("name"));
                city.setPhoto(record.get("photo").getBytes(StandardCharsets.UTF_8));
                return city;}).collect(Collectors.toList());
            repository.saveAll(cityList);
        }



    }
}
