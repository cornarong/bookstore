package com.mytoy.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private Long id;
    @NotBlank
    private String uid;
    @NotBlank(message = "필수 항목입니다.")
    private String name;
    @NotBlank(message = "필수 항목입니다.")
    private String nickname;
    @NotBlank
    private String gender;
    @NotBlank
    private String birth;
    @NotBlank(message = "필수 항목입니다.")
    private String phone;
    private String email;
    @NotBlank(message = "필수 항목입니다.")
    private String postcode;
    @NotBlank(message = "필수 항목입니다.")
    private String address;
    private String detailAddress;
    @NotBlank
    private String since;
    private List<String> roles;

    /* 첨부파일 */
    private MultipartFile profile; // 프로필 객체
    private String profileName; // 프로필 이미지 파일명
    private String profilePath; // 프로필 이미지 물리경로
}
