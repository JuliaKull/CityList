package com.kull.citylist.service.mapper;

import org.mapstruct.*;

import java.util.List;

public interface EntityMapper <E, D>{

    E toEntity(D dto);


    List<E> toEntities(List<D> entityList);

    D toDto(E entity);


    List<D> toDtos(List<E> entityList);


    void update(@MappingTarget E entity,  D dto);
}
