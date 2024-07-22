package com.viewmore.poksin.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    // 아이디
    private String username;
    // 비밀번호
    private String password;
    // 전화번호
    private String phoneNum;
    // 긴급 연락처
    private String emergencyNum;
    // 주소
    private String address;
    // 전화번호 공개 비공개 여부
    private boolean phoneOpen;
    // 긴급 연락처 공개 비공개 여부
    private boolean emergencyOpen;
    // 주소 공개 비공개 여부
    private boolean addressOpen;

    public boolean getphoneOpen() {
        return phoneOpen;
    }

    public boolean getEmergencyOpen() {
        return emergencyOpen;
    }

    public boolean addressOpen() {
        return addressOpen;
    }
}
