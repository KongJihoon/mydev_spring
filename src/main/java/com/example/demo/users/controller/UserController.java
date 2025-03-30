package com.example.demo.users.controller;


import com.example.demo.common.dto.CheckDto;
import com.example.demo.common.dto.ResultDto;
import com.example.demo.users.dto.SendMailDto;
import com.example.demo.users.dto.SignUpDto;
import com.example.demo.users.service.MailService;
import com.example.demo.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "1. USER")
public class UserController {

    private final UserService userService;

    private final MailService mailService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입")
    public ResponseEntity<ResultDto<SignUpDto.Response>> signup(@RequestBody @Validated SignUpDto.Request request) {

        ResultDto<SignUpDto.Response> responseResultDto = userService.signUp(request);

        return ResponseEntity.ok(responseResultDto);

    }

    @PostMapping("/sendMail")
    public ResponseEntity<CheckDto> sendMail(@RequestParam @Validated SendMailDto.Request request) {

        CheckDto checkDto = mailService.sendEmail(request);

        return ResponseEntity.ok(checkDto);
    }

    @PostMapping("/verify")
    public ResponseEntity<CheckDto> verifyCode(
            @RequestParam String email,
            @RequestParam String code
    ) {

        CheckDto checkDto = mailService.verifyEmail(email, code);

        return ResponseEntity.ok(checkDto);

    }

}
