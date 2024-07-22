package com.viewmore.poksin.entity;

import com.viewmore.poksin.dto.UpdateUserDTO;
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
    // admin 계정인가?
    private String role;

    public boolean getphoneOpen() {
        return phoneOpen;
    }

    public boolean getEmergencyOpen() {
        return emergencyOpen;
    }

    public boolean getAddressOpen() {
        return addressOpen;
    }

    public void updateUser(UpdateUserDTO updateUserDTO) {
        this.address = updateUserDTO.getAddress() == null ? this.address : updateUserDTO.getAddress();
        this.emergencyNum = updateUserDTO.getEmergencyNum() == null ? this.emergencyNum : updateUserDTO.getEmergencyNum();
        this.phoneNum = updateUserDTO.getPhoneNum() == null ? this.phoneNum : updateUserDTO.getPhoneNum();
        this.phoneOpen = updateUserDTO.getphoneOpen();
        this.emergencyOpen = updateUserDTO.getEmergencyOpen();
        this.addressOpen = updateUserDTO.getAddressOpen();

    }
}
