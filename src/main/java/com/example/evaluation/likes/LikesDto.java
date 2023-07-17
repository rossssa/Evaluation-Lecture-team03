package com.example.evaluation.likes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LikesDto {
    Long id;
    private Long userId;
    private Long reviewId;

    @Builder
    public LikesDto(Long id, Long userId, Long reviewId) {
        this.id = id;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
