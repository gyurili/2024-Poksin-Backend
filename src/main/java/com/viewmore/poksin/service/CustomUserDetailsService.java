package com.viewmore.poksin.service;

import com.viewmore.poksin.dto.user.CustomUserDetails;
import com.viewmore.poksin.entity.CounselorEntity;
import com.viewmore.poksin.entity.UserEntity;
import com.viewmore.poksin.repository.CounselorRepository;
import com.viewmore.poksin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CounselorRepository counselorRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if (userEntity.isPresent()) {
            return new CustomUserDetails(userEntity.get());
        } else {
            Optional<CounselorEntity> counselorEntity = counselorRepository.findByUsername(username);
            if (counselorEntity.isPresent()) {
                return new CustomUserDetails(counselorEntity.get());
            } else {
                throw new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username);
            }
        }
    }
}

