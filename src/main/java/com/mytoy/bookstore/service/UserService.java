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
    public User save(UserDto userDto) throws IOException {
        // ModelMapper -> MapStruct 리팩토링 진행. (ModelMapper은 런타임 시점에 리플렉션이 발생하므로 성능저하가 심함)

//        ModelMapper modelMapper = new ModelMapper();
//        User user = modelMapper.map(userDto, User.class);

        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.toUserEntity(userDto);

        /* 비밀번호 암호화 */
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        /* 사용자 기본 권한 : ROLE_USER */
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        /* 내장타입 주소 */
        Address address = new Address(userDto.getPostcode(), userDto.getAddress(), userDto.getDetailAddress());
        user.setAddress(address);
        /* 파일 업로드 처리*/
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

        return userRepository.save(user);
    }

    /* 유저 상세정보 */
    public UserDto UserDetail(Long id){
        User findUser = userRepository.findById(id).orElse(null);

        UserMapper userMapper = new UserMapperImpl();
        UserDto userDto = userMapper.toUserDto(findUser);

        return userDto;
    }
}
