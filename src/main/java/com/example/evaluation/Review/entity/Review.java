package com.example.evaluation.Review.entity;

import com.example.evaluation.Lecture.entity.Lecture;
import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.User.entity.User;
import com.example.evaluation.likes.Likes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name="star")
    private Long star; //별점 1~5

    @OneToMany(
            mappedBy = "review",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )

    @JsonBackReference
    private List<Likes> likesList=new ArrayList<>();

    @Builder
    public Review(Long id, String content, User user, Lecture lecture, Long star) {
        this.id = id;
        this.content = content;
        this.user = user; //User클래스 필드에 해당하는 값 user?
        this.lecture = lecture;
        this.star=star;
    }

    public Review(String content, Long userId, Long lecId, Long star) {
        this.id=id;
        this.content = content;
        this.user = new User();
        this.user.setId(userId);
        this.lecture = new Lecture();
        this.lecture.setId(lecId);
        this.star = star;
    }

    public ReviewDto toDto(){
        return ReviewDto.builder()
                .reviewId(id)
                .userId(user)
                .lecId(lecture)
                .content(content)
                .likes(likes)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }



}
