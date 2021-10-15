package com.mytoy.bookstore.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserDetailForm {

    private String uid;
    private String name;
    private String nickname;
    private String gender;
    private LocalDate birth;
    private String phone;
    private String email;
    private String postcode;
    private String address;
    private String detailAddress;
    private LocalDate since;

    private MultipartFile profile; // 프로필 이미지
}
