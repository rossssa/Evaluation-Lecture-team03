package com.example.evaluation.Review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class PostReviewReq {
    private String content;
    private Long star;

    @Builder
    public PostReviewReq(String content, Long star) {
        this.content = content;
        this.star = star;
    }
}