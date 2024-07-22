package com.viewmore.poksin.dto.evidence;

import com.viewmore.poksin.entity.CategoryTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEvidenceDTO {
    // 자료 제목
    private String title;
    // 자료 상세 설명
    private String description;
    // 자료 타입
    private CategoryTypeEnum type;
}
