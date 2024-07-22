package com.viewmore.poksin.service;

import com.viewmore.poksin.dto.RegisterDTO;
import com.viewmore.poksin.entity.UserEntity;
import com.viewmore.poksin.exception.DuplicateUsernameException;
import com.viewmore.poksin.repository.UserRepository;
import com.viewmore.poksin.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void register(RegisterDTO registerDTO) {

        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String phoneNum = registerDTO.getPhoneNum();
        String emergencyNum = registerDTO.getEmergencyNum();
        String address = registerDTO.getAddress();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            throw new DuplicateUsernameException("중복된 아이디가 존재합니다.");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .phoneNum(phoneNum)
                .emergencyNum(emergencyNum)
                .address(address)
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);
    }
}
