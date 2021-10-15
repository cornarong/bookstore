package com.mytoy.bookstore.form;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserForm {

    @NotBlank(message = "필수 항목입니다.")
    private String uid;
    @NotBlank(message = "필수 항목입니다.")
    private String password;
    @NotBlank(message = "필수 항목입니다.")
    private String name;
    @NotBlank(message = "필수 항목입니다.")
    private String nickname;
    @NotBlank(message = "필수 항목입니다.")
    private String gender;
    @NotBlank(message = "필수 항목입니다.")
    private String birth;
    @NotBlank(message = "필수 항목입니다.")
    private String phone;
    private String email;

    @NotBlank(message = "필수 항목입니다.")
    private String postcode;
    @NotBlank(message = "필수 항목입니다.")
    private String address;
    private String detailAddress;

    private MultipartFile profile; // 프로필 이미지
}
