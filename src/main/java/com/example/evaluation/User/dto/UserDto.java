package com.example.evaluation.User.dto;

import com.example.evaluation.User.entity.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    private String studentNum;

    private String password;
    private String name;
    private String major;


    public User toEntity(){
        return User.builder()
                .studentNum(studentNum)
                .name(name)
                .major(major)
                .password(password)
                .build();
    }

}
