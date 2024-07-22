package com.viewmore.poksin.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // 자료 제목
    private String title;
    // 자료 상세 설명
    private String description;
    // 자료와 함께 첨부하는 파일 (url)
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String fileUrls;

    public void setFileUrls(List<String> fileUrls) throws JsonProcessingException {
        // JSON 문자열로 변환
        this.fileUrls = new ObjectMapper().writeValueAsString(fileUrls);
    }

    public List<String> getFileUrls() throws JsonProcessingException {
        // JSON 문자열을 List<String>으로 변환
        return new ObjectMapper().readValue(this.fileUrls, new TypeReference<List<String>>(){});
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
