package com.viewmore.poksin.dto;

import com.viewmore.poksin.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    // open 여부
    private boolean open;
    // 아이디
    private String username;
    // 전화번호
    private String phoneNum;
    // 긴급 연락처
    private String emergencyNum;
    // 주소
    private String address;
    // 상담사인지 일반 유저인지 구분
    private String role;
    // 가입일
    private LocalDateTime createdAt;

    public static UserResponseDTO toDto(UserEntity entity) {
        if (entity.getOpen()) {
            return UserResponseDTO.builder()
                    .open(entity.getOpen())
                    .username(entity.getUsername())
                    .phoneNum(entity.getPhoneNum())
                    .emergencyNum(entity.getEmergencyNum())
                    .address(entity.getAddress())
                    .role(entity.getRole())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }
        return UserResponseDTO.builder()
                .open(entity.getOpen())
                .username(entity.getUsername())
                .emergencyNum(entity.getEmergencyNum())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
