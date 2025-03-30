package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {


    INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 입력 값입니다."),
    INVALID_PATTERN(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 패턴입니다."),
    PAST_DATE_REQUIRE(HttpStatus.BAD_REQUEST.value(), "과거 날짜를 입력해주세요."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일 입니다."),
    EXIST_LOGIN_ID(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 아이디입니다."),
    EXIST_NICKNAME(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 닉네임입니다."),
    NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST.value(), "이메일이 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 서버 오류입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST.value(), "유효하지않은 인증번호입니다.");

    private final int status;
    private final String message;

}
