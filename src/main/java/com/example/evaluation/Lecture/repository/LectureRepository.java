package com.example.evaluation.Lecture.repository;

import com.example.evaluation.Lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
