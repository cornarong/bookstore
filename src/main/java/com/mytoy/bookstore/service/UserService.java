package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.UserSaveDto;
import com.mytoy.bookstore.dto.UserInfoDto;
import com.mytoy.bookstore.mapper.UserMapper;
import com.mytoy.bookstore.mapper.UserMapperImpl;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* 회원 가입 */
    @Transactional(readOnly = false)
    public User save(UserSaveDto userSaveDto) throws IOException {
        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.toUserEntity(userSaveDto);
        /* 비밀번호 암호화 */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    /* 회원정보 수정 */
    @Transactional(readOnly = false)
    public void update(Long userId, UserInfoDto userInfoDto) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        user.update(userInfoDto);
    }

    /* 회원 상세정보 */
    public UserInfoDto detail(Long id){
        User findUser = userRepository.findById(id).orElse(null);
        UserMapper userMapper = new UserMapperImpl();
        UserInfoDto userInfoDto = userMapper.toUserInfoDto(findUser);
        return userInfoDto;
    }
}
