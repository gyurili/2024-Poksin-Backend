package com.viewmore.poksin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 닉네임
    private String username;
    // 비밀번호
    private String password;
    // 전화번호
    private String phoneNum;
    // 긴급 연락처
    private String emergencyNum;
    // 주소
    private String address;
    // admin 계정인가?
    private String role;

}
