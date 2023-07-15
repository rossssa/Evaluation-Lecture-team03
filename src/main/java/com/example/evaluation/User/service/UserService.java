package com.example.evaluation.User.service;

import com.example.evaluation.User.dto.LoginReq;
import com.example.evaluation.User.dto.LoginRes;
import com.example.evaluation.User.dto.UserDto;
import com.example.evaluation.User.entity.User;
import com.example.evaluation.User.repository.UserRepository;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponseStatus;
import com.example.evaluation.global.jwt.JwtService;
import jdk.jshell.spi.ExecutionControlProvider;
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

    public void validateUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
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
            Long userId = user.get().getId();
            String jwt = jwtService.createJwt(userId);
            return new LoginRes(userId, user.get().getStudentNum(), user.get().getName(), user.get().getMajor(), jwt);
        }
        else{
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }


    public void deleteUser(Long userId) {

        try{
            validateUser(userId);
        } catch(Exception e){
            throw new BaseException(INVALID_USER_JWT);
        }

        userRepository.deleteUserById(userId);


//        Optional<User> userOptional = userRepository.findByStudentNum(loginReq.getStudentNum());    //null값을 받아올 수도 있기에 optional로
//        User user = userOptional.orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
//        //useroptional에 값이 존재하면 반환(삭제할 사용자 존재시), 존재x면 예외 발생

//        String encryptedPwd;
//        try {
//            encryptedPwd = new com.example.evaluation.global.jwt.SHA256().encrypt(loginReq.getPassword());
//        } catch (Exception ignored) {
//            throw new BaseException(BaseResponseStatus.PASSWORD_DECRYPTION_ERROR);
//        }
//
//        if (user.getPassword().equals(encryptedPwd)) {  //일치시
//            userRepository.delete(user);    //회원 삭제(탈퇴)
//        } else {
//            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);    //예외 발생시킴
//        }
    }

    public User getUserById(Long userId){       //userid로 해당 사용자 반환
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            throw new BaseException(USER_NOT_FOUND);
        }
        return userOptional.get();
    }

}