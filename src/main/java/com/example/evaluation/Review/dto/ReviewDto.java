
package com.example.evaluation.Review.dto;

import com.example.evaluation.Lecture.entity.Lecture;
import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.User.entity.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
//@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private User userId;
    private Lecture lecId;
    private String content;
    private Long likes;
    private String createdAt;
    private String updatedAt;
    private Long star;

    //    @Builder
    public ReviewDto(Long reviewId, User userId, Lecture lecId, String content, Long likes, String createdAt, String updatedAt,Long star) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.lecId = lecId;
        this.content = content;
        this.likes=likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.star=star;
    }
    public Review toEntity(Long userId, Long lecId, String content, Long star){ //ReviewDto객체를 Review라는 엔티티로 변환해주는 작업 ###

        return Review.builder()
                .user(this.userId)
                .lecture(this.lecId)
                .content(this.content)
                .star(this.star)
                .build();
    }


}
