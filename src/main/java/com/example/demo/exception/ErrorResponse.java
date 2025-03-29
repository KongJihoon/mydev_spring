package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int httpStatus;
    private ErrorCode errorCode;
    private String message;
    private List<String> details;

    // 메서드 오버로딩 사용

    /**
     * 변수로 error객체가 들어오면 ErrorResponse 클래스 변수 설정
     */
    public ErrorResponse(ErrorCode errorCode) {
        this.httpStatus = errorCode.getStatus();
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }



    // details가 추가로 들어올시 List형태로 설정
    public ErrorResponse(ErrorCode errorCode, List<String> details) {
        this.httpStatus = errorCode.getStatus();
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.details = details;
    }




}
