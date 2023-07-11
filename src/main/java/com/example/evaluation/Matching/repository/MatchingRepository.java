package com.example.evaluation.Matching.repository;

import com.example.evaluation.Matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    // 어떤 유저가 들은 강의id들을 가져와야함
    @Query("SELECT m.lecture.id FROM Matching m WHERE m.user.id = :userId")
    List<Long> findLectureIdsByUser_Id(Long userId);
}
