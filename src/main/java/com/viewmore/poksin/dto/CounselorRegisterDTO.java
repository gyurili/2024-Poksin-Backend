package com.viewmore.poksin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselorRegisterDTO {
    // 아이디
    private String username;
    // 비밀번호
    private String password;
    // 전화번호
    private String phoneNum;
    // 전문 분야
    private String specialty;
    // 경력
    private List<String> career = new ArrayList<>();
}
