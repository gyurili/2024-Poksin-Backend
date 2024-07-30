package com.viewmore.poksin.controller;

import com.viewmore.poksin.dto.response.ResponseDTO;
import com.viewmore.poksin.dto.user.CounselorRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "유저 API", description = "유저와 관련된 모든 API")
public interface CounselorAPI {

    @Operation(summary = "[상담사] 회원가입",  description = "username, password, 전화번호, 전공, 경력을 입력하여 상담사 회원가입을 진행합니다.")
    public ResponseEntity<ResponseDTO> registerCounselor(@RequestBody CounselorRegisterDTO counselorRegisterDTO);

    @Operation(summary = "[상담사] 정보 조회")
    public ResponseEntity<ResponseDTO> counselorMypage();
}
