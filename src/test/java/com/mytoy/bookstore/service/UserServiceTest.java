package com.mytoy.bookstore.service;

import com.mytoy.bookstore.model.Role;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

//    @Test
//    @Rollback(false)
//    public void save(){
//        User user = new User();
//        user.setUid("아이디");
//        user.setPassword("비밀번호");
//        Role role = new Role();
//        role.setId(1L);
//        user.getRoles().add(role);
//        User savedUser = userRepository.save(user);
//        userRepository.findById(savedUser.getId());
//    }

    @Test
    public void test(){

    }



}