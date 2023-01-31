package com.kull.citylist.service.mapper;

import com.kull.citylist.dto.UserDto;
import com.kull.citylist.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDto> {
}
