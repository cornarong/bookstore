package com.mytoy.bookstore.service;

import com.mytoy.bookstore.form.UserDetailForm;
import com.mytoy.bookstore.form.UserForm;
import com.mytoy.bookstore.model.Address;
import com.mytoy.bookstore.model.Role;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

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


        // ModelMapper -> MapStruct 리팩토링 진행. (ModelMapper은 런타임 시점에 리플렉션이 발생하므로 성능저하가 심함)

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

    /* 유저 상세정보 */
    public UserDetailForm UserDetail(User user){
        UserDetailForm userDetailForm = new UserDetailForm();
        userDetailForm.setUid(user.getUid());
        userDetailForm.setName(user.getName());
        userDetailForm.setNickname(user.getNickname());
        userDetailForm.setGender(user.getGender());
        userDetailForm.setBirth(user.getBirth());

        return userDetailForm;
    }
}
