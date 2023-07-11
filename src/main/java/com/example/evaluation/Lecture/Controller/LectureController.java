package com.example.evaluation.Lecture.Controller;

import com.example.evaluation.Lecture.dto.LectureDto;
import com.example.evaluation.Lecture.service.LectureService;
import com.example.evaluation.Review.dto.ReviewDto;
import com.example.evaluation.global.BaseException;
import com.example.evaluation.global.BaseResponse;
import com.example.evaluation.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture/my")
public class LectureController {

    private final LectureService lectureService;
    private final JwtService jwtService;
    private final Logger logger= LoggerFactory.getLogger(LectureController.class);


    @GetMapping("")
    public BaseResponse<List<LectureDto>> getAllMyLecture() throws BaseException {
        Long userId = jwtService.getUserId();
        logger.info(""+userId);
        return new BaseResponse<>(this.lectureService.getAllMyLecture(userId));
    }


}
