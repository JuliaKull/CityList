package com.kull.citylist.service.mapper;

import com.kull.citylist.dto.CityDto;
import com.kull.citylist.model.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<City, CityDto> {
}
