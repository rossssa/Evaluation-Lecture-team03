package com.example.evaluation.Review.repository;

import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByLecture_IdOrderByIdDesc(Long lecId);
    Review findTopByOrderByLikesDesc();


    @Modifying
    @Query("UPDATE Review r SET r.likes = r.likes - 1 WHERE r.id = :reviewId AND r.likes > 0")
    void decreaseLikes(Long reviewId);

    @Modifying // update or delete 쿼리 실행을 알려줌
    @Query("UPDATE Review r SET r.likes = r.likes + 1 WHERE r.id = :reviewId")
    void upLikes(Long reviewId);
}


