package com.example.demo.common.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class CheckDto {

    private Boolean success;

    private String message;

}
