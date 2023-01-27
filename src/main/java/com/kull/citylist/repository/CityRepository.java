package com.kull.citylist.repository;

import com.kull.citylist.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends MongoRepository<City,String> {

    Optional<City> findByName(String name);

    @Override
   Page<City> findAll(Pageable pageable);


}
