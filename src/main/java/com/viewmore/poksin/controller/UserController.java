package com.viewmore.poksin.controller;

import com.viewmore.poksin.code.SuccessCode;
import com.viewmore.poksin.dto.RegisterDTO;
import com.viewmore.poksin.dto.UserResponseDTO;
import com.viewmore.poksin.response.ResponseDTO;
import com.viewmore.poksin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_REGISTER, null));
    }

    @GetMapping("/mypage")
    public ResponseEntity<ResponseDTO> mypage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponseDTO response = userService.mypage(username);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_RETRIEVE_USER, response));
    }
}
