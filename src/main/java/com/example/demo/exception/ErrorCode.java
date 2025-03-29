package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {


    INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 입력 값입니다."),
    INVALID_PATTERN(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 패턴입니다."),
    PAST_DATE_REQUIRE(HttpStatus.BAD_REQUEST.value(), "과거 날짜를 입력해주세요.");

    private final int status;
    private final String message;

}
