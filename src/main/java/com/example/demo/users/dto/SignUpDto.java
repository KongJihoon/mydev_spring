package com.example.demo.users.dto;

import com.example.demo.entity.UserEntity;
import com.example.demo.users.type.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class SignUpDto {


    // 회원가입을 위한 Request
    // Validation활용 유효성 검사
    // 클라이언트로부터 Request값을 받는 내부 클래스
    // Request값으로받은 데이터 UserEntity로 전달 및 DB저장 용도
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    public static class Request {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()])[a-zA-Z\\d~!@#$%^&*()]{8,13}$")
        private String password;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()])[a-zA-Z\\d~!@#$%^&*()]{8,13}$")
        private String checkPassword;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞게 입력해주세요.")
        private String email;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "별명을 입력해주세요.")
        private String nickname;

        @JsonFormat(shape = JsonFormat.Shape.STRING
        , pattern = "yyyyMMdd"
        , timezone = "Asia/Seoul")
        private LocalDate birthday;

        @NotBlank(message = "성별을 입력해주세요.")
        private String genderType;

        private List<String> roles;

        public static UserEntity toEntity(Request request) {

            return UserEntity.builder()
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .birthday(request.getBirthday())
                    .gender(GenderType.valueOf(request.getGenderType()))
                    .roles(List.of("ROLE_USER"))
                    .build();
        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        // UserDto를 사용하여 클라이언트에게 응답값제공

        private int userId;

        private String loginId;

        private String email;

        private String name;

        private String nickname;

        private LocalDate birthday;

        private String genderType;

        private List<String> roles;

        public static Response fromDto(UserDto userDto) {

            return Response.builder()
                    .userId(userDto.getUserId())
                    .loginId(userDto.getLoginId())
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .nickname(userDto.getNickname())
                    .birthday(userDto.getBirthday())
                    .genderType(userDto.getGenderType())
                    .roles(userDto.getRoles())
                    .build();


        }

    }

}
