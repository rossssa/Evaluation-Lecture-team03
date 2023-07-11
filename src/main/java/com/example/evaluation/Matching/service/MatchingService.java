package com.example.evaluation.Matching.service;

import com.example.evaluation.Matching.repository.MatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;

    @Autowired
    public MatchingService(MatchingRepository matchingRepository) {
        this.matchingRepository = matchingRepository;
    }

    public List<Long> findLectureIdsByUserId(Long userId) {
        return matchingRepository.findLectureIdsByUser_Id(userId);
    }
}
