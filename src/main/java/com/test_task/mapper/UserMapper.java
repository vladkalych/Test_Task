package com.test_task.mapper;

import com.test_task.dto.UserDto;
import com.test_task.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
