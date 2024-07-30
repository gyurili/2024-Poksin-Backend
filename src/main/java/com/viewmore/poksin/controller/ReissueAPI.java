package com.viewmore.poksin.controller;

import com.viewmore.poksin.dto.response.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Tag(name = "유저 API", description = "유저와 관련된 모든 API")
public interface ReissueAPI {
    @Operation(summary = "[일반 유저, 상담사] refresh token 재발급")
    public ResponseEntity<ResponseDTO> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
