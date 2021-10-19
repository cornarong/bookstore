package com.mytoy.bookstore.form;

import com.mytoy.bookstore.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
public class UserDto {

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

    /* Entity -> DTO 변환시 사용하는 추가 필드 */
    private String profileUseDto; // 이미지 파일명
    private String profilePathUseDto; // 이미지 파일경로
    private String sinceUseDto; // 가입일
    private List<String> roleUseDto; // 권한
    private Long id; // userId
}

