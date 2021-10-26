package com.mytoy.bookstore.dto;

import com.mytoy.bookstore.model.Address;
import com.mytoy.bookstore.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;            // ID
    @NotBlank(message = "필수 항목입니다.")
    private String uid;         // 계정 아이디
    @NotBlank(message = "필수 항목입니다.")
    private String password;    // 계정 비밀번호
    @NotBlank(message = "필수 항목입니다.")
    private String name;        // 이름
    @NotBlank(message = "필수 항목입니다.")
    private String nickname;    // 닉네임
    @NotBlank(message = "필수 항목입니다.")
    private String gender;      // 성별
    @NotBlank(message = "필수 항목입니다.")
    private String birth;       // 생년월일
    @NotBlank(message = "필수 항목입니다.")
    private String phone;       // 휴대전화
    private String email;       // 이메일
    private String since;       // 가입일

    @NotBlank(message = "필수 항목입니다.")
    private String postcode;        // 우편번호
    @NotBlank(message = "필수 항목입니다.")
    private String address;         // 주소
    private String detailAddress;   // 상세주소

    private List<String> roles;     // 권한

    private MultipartFile profile; // 프로필 이미지 객체
    private String profileName;    // 프로필 이미지 파일명
    private String profilePath;    // 프로필 이미지 물리경로

    /* 주소 처리 */
    public void saveAddress(Address address){
        this.postcode = address.getPostcode();
        this.address = address.getAddress();
        this.detailAddress = address.getDetailAddress();
    }
    
    /* 권한 처리 */
    public void saveRole(List<Role> roles){
        this.roles = new ArrayList<>();
        for (Role role : roles){
            this.roles.add(role.getName());
        }
    }

    /* 첨부파일 기본값 처리 */
    public void defaultThumbnail(){ // DB의 이미지 값이 'NULL' 일 경우 노출 할 클라이언트단의 DTO 기본값 처리.
        this.profileName = "noImage.jpg";
        this.profilePath = "";
    }
}
