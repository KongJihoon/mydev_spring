package com.example.demo.entity;

import com.example.demo.users.type.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType gender;

    @Column(nullable = false)
    private String nickname;


    /**
     * @ElementCollection -> 값 컬렉션을 테이블로 관리할 때 사용하는 어노테이션
     * @CollectionTable -> 저장할 테이블을 명시
     * fetch -> Eager: 엔티티를 조회할때 무조건 같이 조회
     * fetch -> LAZY: 실제 사용될 때까지는 안불러옴
     */


    @ElementCollection
    @CollectionTable(
            name = "user_role", joinColumns = @JoinColumn(name = "user_id")
    )
    private List<String> roles;

    @Builder.Default
    @Column(nullable = false)
    private boolean emailAuth = false;


    public void confirmEmailAuth() {
        this.emailAuth = true;
    }

}
