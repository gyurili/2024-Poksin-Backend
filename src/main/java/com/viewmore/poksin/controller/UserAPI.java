package com.viewmore.poksin.controller;

import com.viewmore.poksin.dto.response.ResponseDTO;
import com.viewmore.poksin.dto.user.RegisterDTO;
import com.viewmore.poksin.dto.user.UpdateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "유저 API", description = "유저와 관련된 모든 API")
public interface UserAPI {
    @Operation (summary = "[일반 유저] 회원가입")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody RegisterDTO registerDTO);

    @Operation(summary = "[일반 유저] 유저 정보 조회")
    public ResponseEntity<ResponseDTO> mypage();

    @Operation(summary = "[일반 유저] 유저 정보 수정")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO);

    @Operation(summary = "[일반 유저] 회원 탈퇴")
    public ResponseEntity deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
