package com.viewmore.poksin.dto;

import com.viewmore.poksin.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    // 닉네임
    private String username;
    // 전화번호
    private String phoneNum;
    // 긴급 연락처
    private String emergencyNum;
    // 주소
    private String address;

    public static UserResponseDTO toDto(UserEntity entity) {
        return UserResponseDTO.builder()
                .username(entity.getUsername())
                .phoneNum(entity.getPhoneNum())
                .emergencyNum(entity.getEmergencyNum())
                .address(entity.getAddress())
                .build();
    }
}
