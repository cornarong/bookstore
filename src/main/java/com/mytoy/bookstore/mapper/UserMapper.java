package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.form.UserDto;
import com.mytoy.bookstore.model.Address;
import com.mytoy.bookstore.model.Role;
import com.mytoy.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@Mapper
public interface UserMapper {

    /* 0. UserDto -> User 기본 샘플 */
//    User editFormtoUserEntity(UserDto userDto);

    /* 1. UserDto -> User */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "address", ignore = true)
    default User toUserEntity(UserDto userDto) throws IOException {
        User user = User.builder()
                .uid(userDto.getUid())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .nickname(userDto.getNickname())
                .birth(LocalDate.parse(userDto.getBirth()))
                .phone(userDto.getPhone())
                .gender(userDto.getGender())
                .email(userDto.getEmail())
                .roles(new ArrayList<>())
                .build();
        /* 사용자 기본 권한 : ROLE_USER */
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        user.setEnabled(true);
        /* 내장타입 주소 */
        Address address = new Address(userDto.getPostcode(), userDto.getAddress(), userDto.getDetailAddress());
        user.setAddress(address);
        /* 파일 업로드 */
        if(userDto.getProfile().getSize() != 0 || !userDto.getProfile().getOriginalFilename().equals("")){
            String baseDir = "D:\\study\\profile_image"; // 저장파일 물리경로
            String filePath = baseDir + "\\" + userDto.getProfile().getOriginalFilename(); // 저장파일명
            userDto.getProfile().transferTo(new File(filePath));
            user.setProfile(userDto.getProfile().getOriginalFilename());
            user.setProfilePath(filePath);
        }else{
            user.setProfile(null);
        }
        /* 가입일 */
        LocalDate date = LocalDate.now();
        user.setSince(date);
        return user;
    }

    /* User -> UserDto */
    @Mapping(target = "address", ignore = true)
    default UserDto toUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .uid(user.getUid())
                .name(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .birth(String.valueOf(user.getBirth()))
                .gender(user.getGender())
                .email(user.getEmail())
                .postcode(user.getAddress().getPostcode())
                .address(user.getAddress().getAddress())
                .detailAddress(user.getAddress().getDetailAddress())
                .sinceUseDto(String.valueOf(user.getSince()))
                .roleUseDto(new ArrayList<String>())
                .build();
        for(Role role : user.getRoles()){
            userDto.getRoleUseDto().add(role.getName());
        }
        if(user.getProfile() == null){
            userDto.setProfileUseDto("noImage.jpg");
            userDto.setProfilePathUseDto("");
        }else {
            userDto.setProfileUseDto(user.getProfile());
            userDto.setProfilePathUseDto(user.getProfilePath());
        }
        return userDto;
    }
}
