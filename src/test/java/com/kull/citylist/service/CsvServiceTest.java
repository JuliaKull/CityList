package com.kull.citylist.service;

import com.kull.citylist.model.City;
import com.kull.citylist.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CsvServiceTest {

    @Mock
    private CityRepository repository;

    @InjectMocks
    private CsvService csvService;



    @Test
    void saveCsvFile() throws IOException {
        List<City> expectedCities = List.of(new City("1", "city1", "photo1"),
                new City("2", "city2", "photo2"));

        String csvContent = "1,city1,photo1\n2,city2,photo2";
        MockMultipartFile file = new MockMultipartFile("file", "file.csv", "text/csv", csvContent.getBytes());


        csvService.saveCsvFile(file);


        verify(repository).saveAll(expectedCities);
    }
}