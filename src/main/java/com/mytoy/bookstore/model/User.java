package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid; // 아이디
    private String password; // 비밀번호
    private String name; // 이름
    private String nickname; // 닉네임
    private String gender; // 성별
    private String email; // 이메일
    private String profile; // 프로필사진
    private String profilePath; // 프로필사진 물리경로
    private LocalDate since;

    private boolean enabled; // 권한 활성화 여부

    @Embedded
    private Address address; // 주소 (postcode, address, detailAddress)

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
}
