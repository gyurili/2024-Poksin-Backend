package com.viewmore.poksin.controller;


import com.viewmore.poksin.code.SuccessCode;
import com.viewmore.poksin.dto.evidence.CreateEvidenceDTO;
import com.viewmore.poksin.dto.evidence.EvidenceResponseDTO;
import com.viewmore.poksin.dto.response.ResponseDTO;
import com.viewmore.poksin.service.EvidenceService;
import com.viewmore.poksin.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evidence")
@RequiredArgsConstructor
@Slf4j
public class EvidenceController {
    private final EvidenceService evidenceService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> createListFile(
            @ModelAttribute CreateEvidenceDTO createEvidenceDTO,
            @RequestParam("fileUrls") List<MultipartFile> fileUrls) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        EvidenceResponseDTO response = evidenceService.updateFile(username, createEvidenceDTO, fileUrls);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_CREATE_EVIDENCE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_CREATE_EVIDENCE, response));
    }

    // 테스트를 위한 조회
    // 추후 생성일을 기준으로 GET 요청, 전체 조회일 경우 기록 개수만 응답
    @GetMapping("/find-all")
    public ResponseEntity<ResponseDTO> findAllEvidence() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EvidenceResponseDTO> response = evidenceService.findAll(username);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_RETRIEVE_EVIDENCE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_RETRIEVE_EVIDENCE, response));

    }
}
