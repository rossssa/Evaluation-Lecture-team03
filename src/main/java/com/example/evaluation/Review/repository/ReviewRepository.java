package com.example.evaluation.Review.repository;

import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByLecture_IdOrderByIdDesc(Long lecId);
    Review findTopByOrderByLikesDesc();

}
