package com.example.evaluation.Review.service;

import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.Review.repository.ReviewRepository;
import com.example.evaluation.User.repository.UserRepository;
import com.example.evaluation.User.service.UserService;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReviewRepository reviewRepository;
    private final UserService userService;


    public List<ReviewDto>
    getAllReview(Long userId, Long lecId){
        logger.info(" "+userId);
        userService.validateUser(userId);

        List<ReviewDto> reviewDtoList = reviewRepository.findAllByLecture_IdOrderByIdDesc(Long.valueOf(lecId)).stream()
                .map(Review::toDto)
                .collect(Collectors.toList()); // 최신순으로 리뷰들
        ReviewDto reviewDto = reviewRepository.findTopByOrderByLikesDesc().toDto(); // 추천수 가장 높은 리뷰 하나
        reviewDtoList.add(reviewDto);
        return reviewDtoList;

    }

}


