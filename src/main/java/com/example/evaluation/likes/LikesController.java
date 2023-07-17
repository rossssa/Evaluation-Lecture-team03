package com.example.evaluation.likes;

import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LikesService likesService;
    private final JwtService jwtService;


    @PostMapping("/{reviewId}")
    public BaseResponse signup(@PathVariable("reviewId") Long lecId) throws BaseException {
        Long userId = jwtService.getUserId();
        this.likesService.postLikes(userId, lecId);
        return new BaseResponse("likelikelike");
    }


}
