package com.example.evaluation.User.service;

import com.example.evaluation.User.dto.LoginReq;
import com.example.evaluation.User.dto.LoginRes;
import com.example.evaluation.User.dto.UserDto;
import com.example.evaluation.User.entity.User;
import com.example.evaluation.User.repository.UserRepository;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponseStatus;
import com.example.evaluation.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static com.example.evaluation.global.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public Long create(UserDto userDto) {
        userRepository.findByStudentNum(userDto.getStudentNum())
                .ifPresent(e->{
                    throw new BaseException(POST_USERS_EXISTS_STUDENTNUM);
                });
        String pwd;
        //암호화
        pwd = new com.example.evaluation.global.jwt.SHA256().encrypt(userDto.getPassword());
        userDto.setPassword(pwd);
        User user = this.userRepository.save(userDto.toEntity());
        return user.getId();
    }


    public LoginRes login(LoginReq loginReq) {
        Optional<User> user = userRepository.findByStudentNum(loginReq.getStudentNum()); // db로부터 암호화된 비밀번호 가져오기
        String encryptPwd;
        try {
            encryptPwd=new com.example.evaluation.global.jwt.SHA256().encrypt(loginReq.getPassword()); // 사용자로부터 입력받은 비밀번호 암호화
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.PASSWORD_DECRYPTION_ERROR);
        }

        if(user.get().getPassword().equals(encryptPwd)){
            int userId = Math.toIntExact(user.get().getId());
            String jwt = jwtService.createJwt(userId);
            return new LoginRes(userId, user.get().getStudentNum(), user.get().getName(), user.get().getMajor(), jwt);
        }
        else{
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

}