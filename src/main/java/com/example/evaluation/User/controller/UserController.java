package com.example.evaluation.User.controller;

import com.example.evaluation.User.dto.LoginReq;
import com.example.evaluation.User.dto.LoginRes;
import com.example.evaluation.User.dto.UserDto;
import com.example.evaluation.User.service.UserService;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.BaseResponseStatus;
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
    //1. 사용자가 userDto에 해당하는 값들을 body에 담아 전송, 서버는 RequestBody로 받아옴
    public BaseResponse<Long> signup(@RequestBody UserDto userDto) throws BaseException {
        return new BaseResponse<>(this.userService.create(userDto)); //2. 받아온 내용들을 create함수에 넣어서 실행 ->
        //5. 리턴받은 userId...?생성된건가..?
    }


    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@RequestBody LoginReq loginReq) throws BaseException {
        return new BaseResponse<>(userService.login(loginReq));
    }


    @DeleteMapping("/exit")
    public BaseResponse<Void> deleteUser() throws BaseException {
        //BaseResponse<Void>: 삭제 작업처럼 데이터가 필요하지 않고 작업 성공 여부만 응답으로 반환할떄 사용
        try {
            Long userId = jwtService.getUserId();

            userService.deleteUser(userId);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }





}
