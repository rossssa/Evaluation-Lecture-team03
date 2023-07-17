package com.example.evaluation.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findLike_idByUser_idAndReview_id(Long userId, Long reviewId);
}
