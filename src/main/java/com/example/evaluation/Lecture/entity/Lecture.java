package com.example.evaluation.Lecture.entity;

import com.example.evaluation.Lecture.dto.LectureDto;
import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.User.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="lecture")
public class Lecture {

   @Id
    @Column(name = "lec_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lec_name", unique = true, nullable = false)
    private String lecName;

    @Column(name="professor")
    private String professor;

    @Column(name="lec_code", unique = true)
    private Long lecCode;

    @Column(name="major")
    private String major;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="updated_at")
    private String updatedAt;

    @OneToMany(
            mappedBy = "lecture",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonBackReference
    private List<Review> reviewList=new ArrayList<>();


    public LectureDto toDto() {
     return LectureDto.builder()
             .lecId(id)
             .lecName(lecName)
             .professor(professor)
             .major(major)
             .lecCode(lecCode)
             .createdAt(createdAt)
             .updatedAt(updatedAt)
             .build();
    }



}
