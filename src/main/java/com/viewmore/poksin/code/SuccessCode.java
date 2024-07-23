package com.viewmore.poksin.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SuccessCode {
    /**
     * User
     */
    SUCCESS_REGISTER(HttpStatus.OK, "회원가입을 성공했습니다."),
    SUCCESS_COUNSELOR_REGISTER(HttpStatus.OK, "상담사 회원가입을 성공했습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "로그인을 성공했습니다. 헤더 토큰을 확인하세요."),
    SUCCESS_RETRIEVE_USER(HttpStatus.OK, "유저 정보를 성공적으로 조회했습니다."),
    SUCCESS_REISSUE(HttpStatus.OK, "토큰 재발급을 성공했습니다."),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "유저 정보를 성공적으로 수정했습니다."),

    /**
     * Evidence
     */
    SUCCESS_CREATE_EVIDENCE(HttpStatus.CREATED, "증거가 성공적으로 생성되었습니다."),
    SUCCESS_RETRIEVE_MONTH_EVIDENCE(HttpStatus.OK, "월별 증거를 성공적으로 조회했습니다."),
    SUCCESS_RETRIEVE_DAY_EVIDENCE(HttpStatus.OK, "일별 증거를 성공적으로 조회했습니다."),
    SUCCESS_DELETE_EVIDENCE(HttpStatus.OK, "증거를 성공적으로 삭제했습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
