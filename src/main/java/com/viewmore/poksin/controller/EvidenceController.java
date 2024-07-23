package com.viewmore.poksin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.viewmore.poksin.code.SuccessCode;
import com.viewmore.poksin.dto.evidence.CreateEvidenceDTO;
import com.viewmore.poksin.dto.evidence.EvidenceResponseDTO;
import com.viewmore.poksin.dto.evidence.MonthEvidenceResponseDTO;
import com.viewmore.poksin.dto.response.ResponseDTO;
import com.viewmore.poksin.entity.CategoryTypeEnum;
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

    // year, month로 증거 기록 조회
    @GetMapping("/get-evidence")
    public ResponseEntity<ResponseDTO> findAllEvidence(
            @RequestParam("year") String year,
            @RequestParam("month") String month
             ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MonthEvidenceResponseDTO> response = evidenceService.findAll(username, year, month);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_RETRIEVE_MONTH_EVIDENCE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_RETRIEVE_MONTH_EVIDENCE, response));

    }

    @GetMapping("/category")
    public ResponseEntity<ResponseDTO> findEvidenceByCategory(
            @RequestParam("name") CategoryTypeEnum name
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EvidenceResponseDTO> response = evidenceService.findEvidenceByCategory(username, name);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_RETRIEVE_MONTH_EVIDENCE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_RETRIEVE_MONTH_EVIDENCE, response));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteEvidence(
            @PathVariable("id") Integer id
    ) throws JsonProcessingException {
        evidenceService.deleteEvidence(id);
        return ResponseEntity
                .status(SuccessCode.SUCCESS_DELETE_EVIDENCE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_EVIDENCE, null));

    }
}
