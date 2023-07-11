package com.example.evaluation.Lecture.service;

import com.example.evaluation.Lecture.dto.LectureDto;
import com.example.evaluation.Lecture.entity.Lecture;
import com.example.evaluation.Lecture.repository.LectureRepository;
import com.example.evaluation.Matching.service.MatchingService;
import com.example.evaluation.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LectureRepository lectureRepository;
    private final UserService userService;
    private final MatchingService matchingService;


    public List<LectureDto> getAllMyLecture(Long userId){
        userService.validateUser(userId);
        // userId의 해당유저가 듣는 lec_id를 다 담아서 가져와
        List<Long> lecIdList = matchingService.findLectureIdsByUserId(userId);
        logger.info(lecIdList.get(1).toString());

        // list가 비어있을때의 예외처리 필요
        List<LectureDto> lectureDtoList = lectureRepository.findAllByIdIn(lecIdList).stream()
                .map(Lecture::toDto)
                .collect(Collectors.toList()); // 최신순으로 리뷰들
        return lectureDtoList;

    }
}
