package com.example.evaluation.likes;

import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.User.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="likes")
public class Likes {

    @Id
    @Column(name = "like_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name="review_id")
    @JsonBackReference
    private Review review;

}
