package com.example.demo.users.service;


import com.example.demo.common.dto.CheckDto;
import com.example.demo.common.service.RedisService;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.users.dto.SendMailDto;
import com.example.demo.users.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Random;

import static com.example.demo.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    private static final int CODE_LENGTH = 6;

    private static final Long EMAIL_TOKEN_EXPIRATION = 600000L;

    private static final String EMAIL_PREFIX = "Email_Auth: ";

    public CheckDto sendEmail(SendMailDto.Request request) {

        String code = createRandomCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        // HTML 템플릿에 변수 바인딩
        Context context = new Context();
        context.setVariable("code", code);

        String html = templateEngine.process("mail/email-auth", context); // 확장자 생략

        boolean exists = userRepository.existsByEmail(request.getEmail());

        if (!exists) {
            throw new CustomException(NOT_EXIST_EMAIL);
        }

        try {

            // 인증번호를 전송할 이메일 세팅
            mimeMessageHelper.setTo(request.getEmail());

            // 제목
            mimeMessageHelper.setSubject("회원가입 이메일 인증코드입니다.");

            mimeMessageHelper.setText(html, true);



            // 이메일 전송
            javaMailSender.send(mimeMessage);

            // 이메일 전송 후 레디스에 Key, Value, 유효시간 저장
            redisService.setDataExpire(EMAIL_PREFIX + request.getEmail(), code, EMAIL_TOKEN_EXPIRATION);

        } catch (MessagingException e) {
            throw new CustomException(INTERNAL_SERVER_ERROR);
        }

        // CheckDto를 활용
        return new CheckDto(true, "이메일 인증번호를 발송하였습니다.");

    }


    public CheckDto verifyEmail(String email, String code) {

        String data = redisService.getData(EMAIL_PREFIX + email);

        if (data == null) {
            throw new CustomException(NOT_EXIST_EMAIL);
        }

        // redis에 저장된 인증번호와 이메일로 전송된 이메일 비교
        boolean validCode = data.equals(code);

        if (!validCode) {
            throw new CustomException(INVALID_CODE);
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_EXIST_EMAIL));

        userEntity.confirmEmailAuth();

        // 인증번호 일치 시 UserEntity emailAuth -> true로 변경 후 저장
        userRepository.save(userEntity);


        // 인증 완료된 인증번호는 redis에서 제거
        redisService.deleteData(EMAIL_PREFIX + email);

        return new CheckDto(validCode, "이메일 인증을 완료하였습니다.");
    }

    // 인증번호 생성
    private String createRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < CODE_LENGTH) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }


}
