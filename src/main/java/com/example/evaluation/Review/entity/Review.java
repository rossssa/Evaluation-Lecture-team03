package com.example.evaluation.Review.entity;

import com.example.evaluation.Lecture.entity.Lecture;
import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.User.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="review")
public class Review {

    @Id
    @Column(name="review_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name="lec_id")
    @JsonBackReference
    private Lecture lecture;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="updated_at")
    private String updatedAt;

    @Column(name="likes")
    private Long likes;

    @Builder
    public Review(Long id, String content, User user, Lecture lecture) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.lecture = lecture;
    }

    public ReviewDto toDto(){
        return ReviewDto.builder()
                .reviewId(id)
                .userId(user.getId())
                .lecId(lecture.getId())
                .content(content)
                .likes(likes)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }



}
