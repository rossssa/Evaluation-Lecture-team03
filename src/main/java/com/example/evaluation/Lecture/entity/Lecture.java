package com.example.evaluation.Lecture.entity;

import com.example.evaluation.Review.entity.Review;
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
    private Long Id;

    @Column(name = "lec_name", unique = true, nullable = false)
    private String lecName;

    @Column(name="professor")
    private String professor;

    @Column(name="lec_code", unique = true)
    private int lecCode;

    @OneToMany(
            mappedBy = "lecture",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonBackReference
    private List<Review> reviewList=new ArrayList<>();

}
