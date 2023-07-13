package com.example.evaluation.likes;

import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.Review.repository.ReviewRepository;
import com.example.evaluation.Review.service.ReviewService;
import com.example.evaluation.User.entity.User;
import com.example.evaluation.User.repository.UserRepository;
import com.example.evaluation.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LikesRepository likesRepository;
    private final UserService userService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    public void postLikes(Long userId, Long reviewId) {
        userService.validateUser(userId);
        Likes result = likesRepository.findLike_idByUser_idAndReview_id(userId, reviewId);
        if(result==null){
    //    if (result.getId().describeConstable().isEmpty()) {
            User user = userRepository.findById(userId).orElse(null);
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if (user != null && review != null) {
                Likes likes = new Likes();
                likes.setUser(user);
                likes.setReview(review);
                likesRepository.save(likes);
            }
            reviewService.up_Likes(reviewId);
        } else {
            likesRepository.deleteById(result.getId());
            reviewService.down_Likes(reviewId);
        }
    }

}






