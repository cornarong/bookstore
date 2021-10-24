package com.mytoy.bookstore.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class UserSaveDto {

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
    private MultipartFile profile; // 프로필 객체
}

