package com.viewmore.poksin.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorEntity extends MainUserEntity{
    // 전화번호
    private String phoneNum;
    // 전문 분야
    private String specialty;
    // 경력
    private List<String> career = new ArrayList<>();
    // 상담 횟수
    private Integer count;

    @Builder(builderMethodName = "counselorEntityBuilder")
    public CounselorEntity(String username, String password, String phoneNum, String specialty, List<String> career, Integer count, String role) {
        super(username, password, role);
        this.phoneNum = phoneNum;
        this.specialty = specialty;
        this.career = career;
        this.count = count;
    }
}
