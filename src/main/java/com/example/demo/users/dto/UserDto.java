package com.example.demo.users.dto;

import com.example.demo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {

    // Entity로부터 DB에 저장된 데이터를 응답하는 Dto
    private int userId;

    private String loginId;

    private String password;

    private String email;

    private String name;

    private String nickname;

    private LocalDate birthday;

    private String genderType;

    private List<String> roles;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserDto fromEntity(UserEntity userEntity) {

        return UserDto.builder()
                .userId(userEntity.getUserId())
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .nickname(userEntity.getNickname())
                .birthday(userEntity.getBirthday())
                .genderType(String.valueOf(userEntity.getGender()))
                .roles(userEntity.getRoles())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }


}
