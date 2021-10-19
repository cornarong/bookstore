package com.mytoy.bookstore.service;

import com.mytoy.bookstore.form.UserDto;
import com.mytoy.bookstore.mapper.UserMapper;
import com.mytoy.bookstore.mapper.UserMapperImpl;
import com.mytoy.bookstore.model.Address;
import com.mytoy.bookstore.model.Role;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* 회원가입 */
    @Transactional(readOnly = false)
    public User save(UserDto userDto) throws IOException {
        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.toUserEntity(userDto);
        /* 비밀번호 암호화 */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    /* 회원수정 */
    @Transactional(readOnly = false)
    public void edit(Long userId, UserDto userDto) throws IOException {
        User findUser = userRepository.findById(userId).orElse(null);
        findUser.editUser(userDto);
    }

    /* 유저 상세정보 */
    public UserDto detail(Long id){
        User findUser = userRepository.findById(id).orElse(null);
        UserMapper userMapper = new UserMapperImpl();
        UserDto userDto = userMapper.toUserDto(findUser);
        return userDto;
    }
}
