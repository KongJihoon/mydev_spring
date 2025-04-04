package com.example.demo.common.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class ResultDto<T> {

    private final String message;
    private final T data;

}
