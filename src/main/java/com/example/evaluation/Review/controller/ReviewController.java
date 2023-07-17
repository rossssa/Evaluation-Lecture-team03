package com.example.evaluation.Review.controller;

import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.service.ReviewService;
import com.example.evaluation.User.dto.LoginReq;
import com.example.evaluation.User.dto.LoginRes;
import com.example.evaluation.User.dto.UserDto;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReviewService reviewService;
    private final JwtService jwtService;


    @GetMapping("/{lecId}")
    public BaseResponse<List<ReviewDto>> signup(@PathVariable("lecId") Long lecId) throws BaseException {
        Long userId = jwtService.getUserId();
        logger.info(""+userId);
        return new BaseResponse<>(this.reviewService.getAllReview(userId, lecId));
    }



}
