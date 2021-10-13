package com.mytoy.bookstore.service;

import com.mytoy.bookstore.form.UserForm;
import com.mytoy.bookstore.model.Address;
import com.mytoy.bookstore.model.Role;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public User save(UserForm userForm){

        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userForm, User.class);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        Address address = new Address(userForm.getAbode());
        user.setAddress(address);

        return userRepository.save(user);
    }
}
