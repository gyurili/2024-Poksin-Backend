package com.viewmore.poksin.dto.evidence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viewmore.poksin.entity.CategoryTypeEnum;
import com.viewmore.poksin.entity.EvidenceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceResponseDTO {
    // id
    private int id;
    // 카테고리
    private CategoryTypeEnum category;
    // 자료 제목
    private String title;
    // 자료 상세 설명
    private String description;
    // 자료와 함께 첨부하는 파일 (url)
    private List<String> fileUrls;
    // 생성일
    private LocalDateTime created_at;

    public static EvidenceResponseDTO toDto(EvidenceEntity entity) throws JsonProcessingException {
        return builder()
                .id(entity.getId())
                .category(entity.getCategory().getName())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .fileUrls(entity.getFileUrls())
                .created_at(entity.getCreatedAt())
                .build();
    }
}
