package com.example.demo.users.service;


import com.example.demo.common.dto.ResultDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.users.dto.SignUpDto;
import com.example.demo.users.dto.UserDto;
import com.example.demo.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.example.demo.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원가입 서비스
     * 1. 비밀번호 일치 확인
     * 2. 중복 체크: 아이디 / 이메일 / 닉네임
     * 3. 비밀번호 암호화 후 DB 저장
     * 4. 저장된 유저 정보를 DTO로 변환하여 응답
     */
    public ResultDto<SignUpDto.Response> signUp(SignUpDto.Request request) {

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        boolean idCheck = userRepository.existsByLoginId(request.getLoginId());
        boolean nicknameCheck = userRepository.existsByNickname(request.getNickname());
        boolean loginIdCheck = userRepository.existsByLoginId(request.getLoginId());

        if (idCheck) {
            throw new CustomException(EXIST_LOGIN_ID);
        }

        if (nicknameCheck) {
            throw new CustomException(EXIST_NICKNAME);
        }

        if (loginIdCheck) {
            throw new CustomException(EXIST_LOGIN_ID);
        }

        UserEntity user = userRepository.save(SignUpDto.Request.toEntity(request));

        return ResultDto.of("회원가입에 성공하였습니다.", SignUpDto.Response.fromDto(UserDto.fromEntity(user)));

    }

    private static String createRandomNum() {

        Random random = new Random();
        int certificationNumber = random.nextInt(9999) + 1;

        return String.valueOf(certificationNumber);

    }

}
