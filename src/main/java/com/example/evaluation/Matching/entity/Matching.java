package com.example.evaluation.Matching.entity;

import com.example.evaluation.Lecture.entity.Lecture;
import com.example.evaluation.User.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="matching")
public class Matching {

    @jakarta.persistence.Id
    @Column(name="matching_id", unique = true, nullable = false)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name="lec_id")
    @JsonBackReference
    private Lecture lecture;


}
