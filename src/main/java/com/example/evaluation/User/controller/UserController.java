package com.example.evaluation.User.controller;

import com.example.evaluation.User.dto.LoginReq;
import com.example.evaluation.User.dto.LoginRes;
import com.example.evaluation.User.dto.UserDto;
import com.example.evaluation.User.service.UserService;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Long> signup(@RequestBody UserDto userDto) throws BaseException {
        return new BaseResponse<>(this.userService.create(userDto));
    }


    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@RequestBody LoginReq loginReq) throws BaseException {
        return new BaseResponse<>(userService.login(loginReq));
    }


}
