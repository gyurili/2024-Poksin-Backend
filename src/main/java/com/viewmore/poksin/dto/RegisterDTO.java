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
    // 공개 비공개 여부
    private boolean open;

    public boolean getOpen() {
        return open;
    }
}
