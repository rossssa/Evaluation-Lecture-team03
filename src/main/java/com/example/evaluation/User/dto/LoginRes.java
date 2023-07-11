package com.example.evaluation.User.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRes {
    private Long userId;
    private String studentNum;
    private String name;
    private String major;
    private String jwt;
}
