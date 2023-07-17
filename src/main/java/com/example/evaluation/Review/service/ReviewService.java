

package com.example.evaluation.Review.service;

import com.example.evaluation.Lecture.entity.Lecture;
import com.example.evaluation.Lecture.repository.LectureRepository;
import com.example.evaluation.Review.dto.PostReviewReq;
import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.Review.repository.ReviewRepository;
import com.example.evaluation.User.entity.User;
import com.example.evaluation.User.repository.UserRepository;
import com.example.evaluation.User.service.UserService;
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
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;


    public List<ReviewDto>
    getAllReview(Long userId, Long lecId){
        logger.info(" "+userId);
        userService.validateUser(userId);
        // list가 비어있을때의 예외처리 필요
        List<ReviewDto> reviewDtoList = reviewRepository.findAllByLecture_IdOrderByIdDesc(Long.valueOf(lecId)).stream()
                .map(Review::toDto)
                .collect(Collectors.toList()); // 최신순으로 리뷰들
        ReviewDto reviewDto = reviewRepository.findTopByOrderByLikesDesc().toDto(); // 추천수 가장 높은 리뷰 하나
        reviewDtoList.add(reviewDto);
        return reviewDtoList;

    }

    public void down_Likes(Long reviewId){
        reviewRepository.decreaseLikes(reviewId);
    }

    public void up_Likes(Long reviewId){
        reviewRepository.upLikes(reviewId);
    }


    //평가 등록
    public void writeReview(PostReviewReq postReviewReq, Long lecId, Long userId){ //3. 사용자가 입력한 내용들 reviewDto를 사용
        // 별점이 0 이상 5 이하인지 검사
        if (postReviewReq.getStar() < 0 || postReviewReq.getStar() > 5) { //3-2. 사용자가 준 reviewDto 중에 star값을 검사함
            throw new IllegalArgumentException("별점은 0부터 5까지 가능합니다.");
        }
        Review review=new Review();
        User user=userRepository.findById(userId).get();
        Lecture lecture=lectureRepository.findById(lecId).get();

        review.setContent(postReviewReq.getContent());
        review.setUser(user);
        review.setLecture(lecture);
        review.setStar(postReviewReq.getStar());
        this.reviewRepository.save(review);
        return;
        //평가 저장
        // return reviewRepository.save(reviewDto.toEntity(userId, lecId, reviewDto.getContent(), reviewDto.getStar())); //3-3. reviewDto를 Entity로 만들어줘야행/ 그리고 저장 -> 다시 컨트롤러로 리턴

    }

    //평가 수정
    public Review updateReview(long userId, long reviewId, PostReviewReq updatedReview){
        Optional<Review> existingReview=reviewRepository.findById(reviewId); //리뷰아이디에 해당하는 리뷰글을 가져옴

        if (existingReview.isPresent()) { //해당 리뷰가 지금 존재한다면,
            Review review  = existingReview.get();//존재하는 리뷰를 가져옴
            if(userId==existingReview.get().getUser().getId()){ //유저 아이디와 지금 리뷰 글쓴이의 아이디 가져와서 비교
                //아이디 일치시
                review.setContent(updatedReview.getContent());
                review.setStar(updatedReview.getStar());
                return reviewRepository.save(review);//저장
            }else{
                //아이디 불일치시
                throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
            }
        } else {
            //존재하지 않는 경우 예외 처리
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }
    }

    //평가 삭제
    public void deleteReview(long userId,long reviewId){//리뷰아이디를 받아오면,
        Optional<Review> existingReview = reviewRepository.findById(reviewId);//기존 리뷰들 중, 해당 리뷰 아이디를 찾는다.
        if (existingReview.isPresent()) {//해당 리뷰가 존재하면,
            if(userId==existingReview.get().getUser().getId()){ //유저 아이디와 지금 리뷰 글쓴이의 아이디 가져와서 비교
                //아이디 일치시
                reviewRepository.deleteById(reviewId);//리뷰를 삭제하는 기능 실행
            }else{
                //아이디 불일치시
                throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
            }
        } else {
            //존재하지 않는 경우 예외 처리
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }
    }

}
