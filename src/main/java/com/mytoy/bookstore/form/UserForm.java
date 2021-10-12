package com.mytoy.bookstore.form;

import com.mytoy.bookstore.model.Address;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embedded;
import java.util.Date;

@Data
public class UserForm {

    private String uid;
    private String password;
    private String name;
    private String nickname;
    private String gender;
    private String birth;
    private String phone;

    private String city;
    private String street;
    private String zipcode;

    private String email;
    private MultipartFile profile; // 프로필 이미지
    private String since;


}
