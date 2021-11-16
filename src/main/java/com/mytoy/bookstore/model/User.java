package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.dto.UserDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // ID
    private String uid;         // 아이디
    private String password;    // 비밀번호
    private String name;        // 이름
    private String nickname;    // 닉네임
    private LocalDate birth;    // 생년월일
    private String phone;       // 휴대전화
    private String gender;      // 성별
    private String email;       // 이메일
    private String profileName; // 프로필 이미지
    private String profilePath; // 프로필 이미지 물리경로
    private LocalDate since;    // 가입일
    private boolean enabled;    // 권한 활성화 여부

    @Embedded
    private Address address;    // 주소 (postcode, address, detailAddress)

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    // cascade = CascadeType.xxx = 클래스(Entitiy) 안의 클래스(Entitiy)까지 적용시키기 위한 설정
    // RESTAPI요청 시 boards의 PK값까지 함께 전달되면 업데이트, PK값 없을 경우 새로 저장
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // orphanRemoval = true 난 필요하지 않음
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Basket> baskets = new ArrayList<>();

    /**
     * 비즈니스 로직
     * 객체 지향에 가깝게 직접 엔티디에 설계함으로써 관리하기에도 편한다.
     */

    /* 인코딩된 비밀번호 저장  */
    public void savePassword(String password){
        this.password = password;
    }

    /* 임베디드 타입 주소 저장 */
    public void saveAddress(String postcode, String address, String detailAddress){
        Address totalAddress = new Address(postcode, address, detailAddress);
        this.address = totalAddress;
    }

    /* 기본 권한 저장 */
    public void saveRole(){
        Role role = new Role();
        role.setId(1L);
        this.roles = new ArrayList<Role>();
        this.roles.add(role);
        this.enabled = true; //활성화 여부
    }

    /* 가입일 저장 */
    public void saveSince(){
        this.since = LocalDate.now();
    }

    /* 프로필 이미지 저장 */
    public void saveProfile(MultipartFile profile) throws IOException {
        if(profile.getSize() != 0){
//            String baseDir = "D:\\study\\profile_image";
//            String filePath = baseDir + "\\" + profile.getOriginalFilename();
            String baseDir = "/home/ec2-user/bookstore/profile/"; // aws 서버
            String filePath = baseDir + profile.getOriginalFilename();
            profile.transferTo(new File(filePath));
            this.profileName = profile.getOriginalFilename();
            this.profilePath = filePath;
        }else{
            this.profileName = null; // 이미지가 없을 경우 DB는 'NULL' 으로 처리.
            this.profilePath = null; // 이미지가 없을 경우 DB는 'NULL' 으로 처리.
        }
    }

    /* 회원 수정 */
    public User edit(UserDto userDto) throws IOException {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.nickname = userDto.getNickname();
        this.address = new Address(userDto.getPostcode(), userDto.getAddress(), userDto.getDetailAddress());
        this.email = userDto.getEmail();
        if(userDto.getProfile().getSize() != 0 || userDto.getProfileName().equals("noImage.jpg")){
            this.saveProfile(userDto.getProfile());
        }else{
            this.profileName = userDto.getProfileName();
            this.profilePath = userDto.getProfilePath();
        }

        // 권한 처리 - 기본 값 : ROLE_USER
        this.roles.clear();

        int len = 1;
        if (userDto.getRoles().contains("ROLE_ADMIN")) len = 3;
        else if (userDto.getRoles().contains("ROLE_MANAGER")) len = 2;

        for (int i = 1; i < len+1; i++) {
            Role role = new Role();
            role.setId(Long.valueOf(i));
            this.roles.add(role);
        }
        return this;
    }
}
