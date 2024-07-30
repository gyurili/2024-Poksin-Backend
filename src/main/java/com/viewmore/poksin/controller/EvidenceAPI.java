package com.viewmore.poksin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viewmore.poksin.dto.evidence.CreateEvidenceDTO;
import com.viewmore.poksin.dto.response.ResponseDTO;
import com.viewmore.poksin.entity.CategoryTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "증거 API", description = "증거와 관련된 모든 API")
public interface EvidenceAPI {
    @Operation(summary = "[일반 유저] 증거 업로드")
    public ResponseEntity<ResponseDTO> createListFile(
            @RequestPart("createEvidenceDTO")  CreateEvidenceDTO createEvidenceDTO,
            @RequestPart("fileUrls") List<MultipartFile> fileUrls
    ) throws IOException;

    @Operation(summary = "[일반 유저] 증거 월별 조회")
    @GetMapping("/get-month-evidence")
    public ResponseEntity<ResponseDTO> findAllEvidenceByMonth(
            @RequestParam("year") String year,
            @RequestParam("month") String month
    );

    @Operation(summary = "[일반 유저] 증거 일별 조회")
    @GetMapping("/get-day-evidence")
    public ResponseEntity<ResponseDTO> findAllEvidenceByDay(
            @RequestParam("year") String year,
            @RequestParam("month") String month,
            @RequestParam("day") String day,
            @RequestParam("category") CategoryTypeEnum category
    );

    @Operation(summary = "[일반 유저] 업로드한 증거 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteEvidence(
            @PathVariable("id") Integer id
    ) throws JsonProcessingException;

    @Operation(summary = "[일반 유저] 분석한 영상 증거 조회")
    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseDTO> detailVideoEvidence(
            @PathVariable("id") Integer id
    ) throws JsonProcessingException;
}
