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

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedFileSystemException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* 회원가입 */
    @Transactional(readOnly = false)
    public User save(UserForm userForm) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userForm, User.class);

        /* 비밀번호 암호화 */
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setEnabled(true);

        /* 사용자 기본 권한 : ROLE_USER */
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);

        /* 내장타입 주소 */
        Address address = new Address(userForm.getPostcode(), userForm.getAddress(), userForm.getDetailAddress());
        user.setAddress(address);

        /* 파일 업로드 처리*/
        if(userForm.getProfile().getSize() != 0 || !userForm.getProfile().getOriginalFilename().equals("")){
            String baseDir = "D:\\study\\profile_image"; // 저장파일 물리경로
            String filePath = baseDir + "\\" + userForm.getProfile().getOriginalFilename(); // 저장파일명
            userForm.getProfile().transferTo(new File(filePath));
            user.setProfile(userForm.getProfile().getOriginalFilename());
            user.setProfilePath(filePath);
        }else{
            user.setProfile(null);
        }

        /* 가입일 */
        LocalDate date = LocalDate.now();
        user.setSince(date);

        return userRepository.save(user);
    }
}
