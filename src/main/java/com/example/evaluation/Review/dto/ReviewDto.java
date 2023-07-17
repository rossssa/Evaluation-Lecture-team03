package com.example.evaluation.Review.dto;

import com.example.evaluation.Review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long userId;
    private Long lecId;
    private String content;
    private Long likes;
    private String createdAt;
    private String updatedAt;

    @Builder
    public ReviewDto(Long reviewId, Long userId, Long lecId, String content, Long likes, String createdAt, String updatedAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.lecId = lecId;
        this.content = content;
        this.likes=likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
