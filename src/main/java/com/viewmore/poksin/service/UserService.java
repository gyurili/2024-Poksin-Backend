package com.viewmore.poksin.service;

import com.viewmore.poksin.dto.user.*;
import com.viewmore.poksin.entity.UserEntity;
import com.viewmore.poksin.exception.DuplicateUsernameException;
import com.viewmore.poksin.repository.CounselorRepository;
import com.viewmore.poksin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CounselorRepository counselorRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void register(RegisterDTO registerDTO) {

        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();

        // 상담사, 일반 유저 아이디 중복 검사
        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            throw new DuplicateUsernameException("중복된 아이디가 존재합니다.");
        }

        isExist = counselorRepository.existsByUsername(username);

        if (isExist) {
            throw new DuplicateUsernameException("중복된 아이디가 존재합니다.");
        }

        UserEntity user = UserEntity.userEntityBuilder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .phoneNum(registerDTO.getPhoneNum())
                .emergencyNum(registerDTO.getEmergencyNum())
                .address(registerDTO.getAddress())
                .phoneOpen(registerDTO.getphoneOpen())
                .emergencyOpen(registerDTO.getEmergencyOpen())
                .addressOpen(registerDTO.getAddressOpen())
                .role("USER")
                .build();

        userRepository.save(user);
    }

    public UserResponseDTO mypage(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));

        return UserResponseDTO.toDto(user);
    }

    @Transactional
    public UserResponseDTO updateUser(String username, UpdateUserDTO updateUserDTO) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));

        user.updateUser(updateUserDTO);

        return UserResponseDTO.toDto(user);
    }
}
