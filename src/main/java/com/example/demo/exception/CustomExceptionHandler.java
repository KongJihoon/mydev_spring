package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice // 서버에 오류 발생시 전역적으로 오류 관리
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ErrorResponse handleCustomException(CustomException e) {


        // ErrorResponse 첫번째 생성자에 직접 커스텀한 Exception 전달후 리턴
        return new ErrorResponse(e.getErrorCode());

    }

    // @Valid, @Validated 등 어노테이션을 사용하여 @NotNull...등을 검사할때 ErrorCode로 커스텀하여 반환하기 위한 클래스
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {


        ErrorResponse errorResponse = getBindError(e.getBindingResult());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // BindingResult -> @Valid 어노테이션으로 유효성 검증을 했을때 실패한 결과를 담은 객체
    private ErrorResponse getBindError(BindingResult bindingResult) {

        ErrorCode errorCode = null;
        List<String> details = new ArrayList<>();


        if (bindingResult.hasErrors()) {

            // FieldError -> 검사에 실패한 각 필드에대한 에러정보
            // 따라서 변수로 들어온 Bind객체들을 돌면서 각 @Valid 검사에 실패한 필드들을 List에담아 리턴하는 메서드다.
            for (FieldError fieldError : bindingResult.getFieldErrors()) {

                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                String bindCode = fieldError.getCode();

                errorCode = switch (Objects.requireNonNull(bindCode)) {
                    case "NotBlank" -> ErrorCode.INVALID_INPUT;
                    case "Pattern" -> ErrorCode.INVALID_PATTERN;
                    case "Email" -> ErrorCode.INVALID_PATTERN;
                    case "Positive" -> ErrorCode.INVALID_PATTERN;
                    case "Past" -> ErrorCode.PAST_DATE_REQUIRE;
                    default -> errorCode;
                };

                details.add(field + ": " + message);


            }


        }

        return new ErrorResponse(errorCode, details);
    }

}
