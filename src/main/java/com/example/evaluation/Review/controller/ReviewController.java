package com.example.evaluation.Review.controller;

import com.example.evaluation.Review.dto.PostReviewReq;
import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.Review.service.ReviewService;
import com.example.evaluation.User.dto.LoginReq;
import com.example.evaluation.User.dto.LoginRes;
import com.example.evaluation.User.dto.UserDto;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.BaseResponseStatus;
import com.example.evaluation.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/{lecId}")
    public BaseResponse writeReview(@PathVariable("lecId") Long lecId, @RequestBody PostReviewReq postReviewReq) { //1.ReviewDto타입에 맞는 review가 인자로 들어옴
        Long userId = jwtService.getUserId();
        logger.info(userId+"");
        // 등록
        this.reviewService.writeReview(postReviewReq, lecId, userId); //2. review를 등록해주기 위해 writeReview함수로 실행 ->
        //4. 최종적으로 Entity로 만들고 저장한 것을 newReview로 정의(?)
        return new BaseResponse("성공");
    }


//  수정전
//    public class InvalidRatingException extends RuntimeException {
//        public InvalidRatingException(String message) {
//            super(message);
//        }
//    }
//    //@ResponseBody
//    public ResponseEntity<Review> writeReview(@RequestBody Review review) {
////        Long userId = jwtService.getUserId();
//        // 등록
//        Review newReview = reviewService.writeReview(review);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
//    }
//    public class InvalidRatingException extends RuntimeException {
//        public InvalidRatingException(String message) {
//            super(message);
//        }
//    }


    @PutMapping("/{reviewId}")
    @ResponseBody
    public ResponseEntity<Review> updatedReview(@PathVariable("reviewId") Long reviewId,@RequestBody PostReviewReq updatedReview) {
        //수정
        try {
            Long userId = jwtService.getUserId();//로그인한 유저와 글쓴이를 비교하기 위해 유저아이디 가져옴
            Review review = reviewService.updateReview(userId,reviewId, updatedReview);
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse<Void> deleteReview(@PathVariable("reviewId") Long reviewId) throws BaseException {
        //삭제
        try {
            Long userId = jwtService.getUserId();//로그인한 유저와 글쓴이를 비교하기 위해 유저아이디 가져옴
            reviewService.deleteReview(userId,reviewId);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}