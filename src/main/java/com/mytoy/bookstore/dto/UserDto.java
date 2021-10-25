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

    private Long id;
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
    private String since;

    @NotBlank(message = "필수 항목입니다.")
    private String postcode;
    @NotBlank(message = "필수 항목입니다.")
    private String address;
    private String detailAddress;

    private List<String> roles;

    /* 첨부파일 */
    private MultipartFile profile; // 프로필 이미지 객체
    private String profileName; // 프로필 이미지 파일명
    private String profilePath; // 프로필 이미지 물리경로

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
    public void defaultThumbnail(){ // DB의 이미지 값이 'NULL' 일 경우 보여지는 DTO 기본값 처리.
        this.profileName = "noImage.jpg";
        this.profilePath = "";
    }
}
