package com.example.evaluation.User.entity;

import com.example.evaluation.Matching.entity.Matching;
import com.example.evaluation.Review.entity.Review;
import com.example.evaluation.User.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user")
public class User {

    @Id
    @Column(name="user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_num", unique = true, nullable = false)
    private String studentNum;

    @Column(name = "password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="major")
    private String major;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonBackReference
    private List<Review> reviewList=new ArrayList<>();



    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    @JsonBackReference
    private List<Matching> matchingList=new ArrayList<>();


    public UserDto toDto(){
        return UserDto.builder()
                .studentNum(studentNum)
                .name(name)
                .major(major)
                .password(password)
                .build();
    }


}
