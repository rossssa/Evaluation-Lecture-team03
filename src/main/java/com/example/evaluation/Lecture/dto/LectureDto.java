package com.example.evaluation.Lecture.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LectureDto {
    private Long lecId;
    private String lecName;
    private String professor;
    private String major;
    private Long lecCode;
    private String createdAt;
    private String updatedAt;

    @Builder
    public LectureDto(Long lecId, String lecName, String professor, String major, Long lecCode, String createdAt, String updatedAt) {
        this.lecId = lecId;
        this.lecName = lecName;
        this.professor = professor;
        this.major = major;
        this.lecCode = lecCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
