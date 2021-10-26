package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.UserDto;
import com.mytoy.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUserEntity(UserDto userDto);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "profile", ignore = true)
    UserDto toUserDto(User user);

}
