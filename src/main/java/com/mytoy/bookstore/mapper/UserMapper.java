package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.UserSaveDto;
import com.mytoy.bookstore.dto.UserInfoDto;
import com.mytoy.bookstore.model.Address;
import com.mytoy.bookstore.model.Role;
import com.mytoy.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@Mapper
public interface UserMapper {

//    User toUserEntity(UserSaveDto userSaveDto);

    /* 1. UserSaveDto -> UserEntity */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "address", ignore = true)
    default User toUserEntity(UserSaveDto userSaveDto) throws IOException {
        User user = User.builder()
                .uid(userSaveDto.getUid())
                .password(userSaveDto.getPassword())
                .name(userSaveDto.getName())
                .nickname(userSaveDto.getNickname())
                .birth(LocalDate.parse(userSaveDto.getBirth()))
                .phone(userSaveDto.getPhone())
                .gender(userSaveDto.getGender())
                .email(userSaveDto.getEmail())
                .roles(new ArrayList<>())
                .build();
        /* 사용자 기본 권한 : ROLE_USER */
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        user.setEnabled(true);
        /* 내장타입 주소 */
        Address address = new Address(userSaveDto.getPostcode(), userSaveDto.getAddress(), userSaveDto.getDetailAddress());
        user.setAddress(address);
        /* 파일 업로드 */
        if(userSaveDto.getProfile().getSize() != 0 || !userSaveDto.getProfile().getOriginalFilename().equals("")){
            String baseDir = "D:\\study\\profile_image"; // 저장파일 물리경로
            String filePath = baseDir + "\\" + userSaveDto.getProfile().getOriginalFilename(); // 저장파일명
            userSaveDto.getProfile().transferTo(new File(filePath));
            user.setProfile(userSaveDto.getProfile().getOriginalFilename());
            user.setProfilePath(filePath);
        }else{
            user.setProfile(null);
        }
        /* 가입일 */
        LocalDate date = LocalDate.now();
        user.setSince(date);
        return user;
    }

    /* UserEntity -> UserInfoDto */
    @Mapping(target = "address", ignore = true)
    default UserInfoDto toUserInfoDto(User user) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
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
                .since(String.valueOf(user.getSince()))
                .roles(new ArrayList<String>())
                .build();
        for(Role role : user.getRoles()){
            userInfoDto.getRoles().add(role.getName());
        }
        if(user.getProfile() == null){
            userInfoDto.setProfileName("noImage.jpg");
            userInfoDto.setProfilePath("");
        }else {
            userInfoDto.setProfileName(user.getProfile());
            userInfoDto.setProfilePath(user.getProfilePath());
        }
        return userInfoDto;
    }
}
