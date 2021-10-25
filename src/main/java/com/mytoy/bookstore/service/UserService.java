package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.UserDto;
import com.mytoy.bookstore.mapper.UserMapper;
import com.mytoy.bookstore.mapper.UserMapperImpl;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* 회원 목록 가져오기 */
    public List<UserDto> all(){
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        for (User user : userList){
            UserMapper userMapper = new UserMapperImpl();
            UserDto userDto = userMapper.toUserDto(user);
            userDto.saveRole(user.getRoles());
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    /* 회원 등록 하기 */
    @Transactional(readOnly = false)
    public User save(UserDto userDto) throws IOException {
        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.toUserEntity(userDto);

        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        user.savePassword(encodePassword);
        user.saveAddress(userDto.getPostcode(), userDto.getAddress(), userDto.getDetailAddress());
        user.saveRole();
        user.saveSince();

        User saveUser = userRepository.save(user);
        return saveUser;
    }

    /* 회원정보 단건 가져오기 */
    public UserDto detail(Long id){
        User user = userRepository.findById(id).orElse(null);

        UserMapper userMapper = new UserMapperImpl();
        UserDto userDto = userMapper.toUserDto(user);

        userDto.saveAddress(user.getAddress());
        userDto.saveRole(user.getRoles());
        if(user.getProfileName() == null) userDto.defaultThumbnail();

        return userDto;
    }

    /* 회원정보 수정하기 */
    @Transactional(readOnly = false)
    public void edit(Long userId, UserDto userDto) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        user.edit(userDto);
    }

}
