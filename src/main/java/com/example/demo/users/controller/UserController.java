package com.example.demo.users.controller;


import com.example.demo.common.ResultDto;
import com.example.demo.users.dto.SignUpDto;
import com.example.demo.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "1. USER")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입")
    public ResponseEntity<ResultDto<SignUpDto.Response>> signup(@RequestBody @Validated SignUpDto.Request request) {

        ResultDto<SignUpDto.Response> responseResultDto = userService.signUp(request);

        return ResponseEntity.ok(responseResultDto);

    }

}
