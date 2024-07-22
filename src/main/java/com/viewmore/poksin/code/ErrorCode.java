package com.viewmore.poksin.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    /**
     * 409 CONFLICT: 중복된 이메일
     */
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 유저 이름입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
