package com.example.evaluation.Lecture.repository;

import com.example.evaluation.Lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findAllByIdIn(List<Long> lectureIds);
}
