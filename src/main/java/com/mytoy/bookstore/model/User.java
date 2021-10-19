package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // id
    private String uid;         // 아이디
    private String password;    // 비밀번호
    private String name;        // 이름
    private String nickname;    // 닉네임
    private LocalDate birth;    // 생년월일
    private String phone;       // 휴대전화
    private String gender;      // 성별
    private String email;       // 이메일
    private String profile;     // 프로필사진
    private String profilePath; // 프로필사진 물리경로
    private LocalDate since;    // 가입일
    private boolean enabled;    // 권한 활성화 여부
    @Embedded
    private Address address;    // 주소 (postcode, address, detailAddress)

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    // cascade = CascadeType.xxx = 클래스(Entitiy) 안의 클래스(Entitiy)까지 적용시키기 위한 설정
    // RESTAPI요청 시 boards의 PK값까지 함께 전달되면 업데이트, PK값 없을 경우 새로 저장
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // orphanRemoval = true 난 필요하지 않음
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> order = new ArrayList<>();

    /* 회원 수정 메소드 */
    public User update(UserInfoDto userInfoDto) throws IOException {
        this.name = userInfoDto.getName();
        this.phone = userInfoDto.getPhone();
        this.nickname = userInfoDto.getNickname();
        this.address = new Address(userInfoDto.getPostcode(), userInfoDto.getAddress(), userInfoDto.getDetailAddress());
        this.email = userInfoDto.getEmail();
        /* 파일 업로드 처리 */
        if(userInfoDto.getProfile().getSize() != 0 || !userInfoDto.getProfile().getOriginalFilename().equals("")){
            String baseDir = "D:\\study\\profile_image"; // 저장파일 물리경로
            String filePath = baseDir + "\\" + userInfoDto.getProfile().getOriginalFilename(); // 저장파일명
            userInfoDto.getProfile().transferTo(new File(filePath));
            this.setProfile(userInfoDto.getProfile().getOriginalFilename());
            this.setProfilePath(filePath);
        }else{
            this.setProfile(null);
        }
        // 권한 변경, default : ROLE_USER
        this.roles.clear();
        if(userInfoDto.getRoles().size() == 0) {
            Role role = new Role();
            role.setId(1L);
            this.roles.add(role);
        }else{
            for(String roleName : userInfoDto.getRoles()){
                Role role = new Role();
                if(roleName.equals("ROLE_USER")) role.setId(1L);
                if(roleName.equals("ROLE_MANAGER")) role.setId(2L);
                if(roleName.equals("ROLE_ADMIN")) role.setId(3L);
                this.roles.add(role);
            }
        }
        return this;
    }
}
