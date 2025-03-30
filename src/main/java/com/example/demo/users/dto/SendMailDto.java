package com.example.demo.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SendMailDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private String email;


    }


}
