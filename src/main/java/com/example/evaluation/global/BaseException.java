package com.example.evaluation.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private BaseResponseStatus status;
}
