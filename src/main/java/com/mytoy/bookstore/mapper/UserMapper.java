package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.form.UserDto;
import com.mytoy.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface UserMapper {

    /* User -> UserDto */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "address", ignore = true)
    User toUserEntity(UserDto userDto);

    /* UserDto -> User */
    @Mapping(target = "address", ignore = true)
    default UserDto toUserDto(User user) { // Builder로 처리해보자. 잠시 대기.
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUid(user.getUid());
        userDto.setName(user.getName());
        userDto.setNickname(user.getNickname());
        userDto.setPhone(user.getPhone());
        userDto.setBirth(String.valueOf(user.getBirth()));
        userDto.setGender(user.getGender());
        userDto.setEmail(user.getEmail());
        userDto.setPostcode(user.getAddress().getPostcode());
        userDto.setAddress(user.getAddress().getAddress());
        userDto.setDetailAddress(user.getAddress().getDetailAddress());
        if(user.getProfile() == null){
            userDto.setProfileUseDto("noImage.jpg");
            userDto.setProfilePathUseDto("");
        }else {
            userDto.setProfileUseDto(user.getProfile());
            userDto.setProfilePathUseDto(user.getProfilePath());
        }
        userDto.setSinceUseDto(String.valueOf(user.getSince()));
        userDto.setRoleUseDto(user.getRoles().get(0).getName());
        return userDto;
    }
}
