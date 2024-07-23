package com.viewmore.poksin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viewmore.poksin.dto.evidence.CreateEvidenceDTO;
import com.viewmore.poksin.dto.evidence.EvidenceResponseDTO;
import com.viewmore.poksin.dto.evidence.MonthEvidenceResponseDTO;
import com.viewmore.poksin.entity.CategoryEntity;
import com.viewmore.poksin.entity.CategoryTypeEnum;
import com.viewmore.poksin.entity.EvidenceEntity;
import com.viewmore.poksin.entity.UserEntity;
import com.viewmore.poksin.exception.CategoryNotFoundException;
import com.viewmore.poksin.exception.EvidenceNotFoundException;
import com.viewmore.poksin.repository.CategoryRepository;
import com.viewmore.poksin.repository.EvidenceRepository;
import com.viewmore.poksin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;

    public EvidenceResponseDTO updateFile(String username, CreateEvidenceDTO createEvidenceDTO, List<MultipartFile> fileUrls) throws IOException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));

        List<String> getUrls = new ArrayList<>();

        CategoryTypeEnum type = createEvidenceDTO.getType();
        for(MultipartFile file : fileUrls) {
            getUrls.add(s3Uploader.upload(file, type.toString().toLowerCase()));
        }

        CategoryEntity category = categoryRepository.findByName(type)
                .orElseThrow(() -> new UsernameNotFoundException("해당 카테고리 이름을 찾을 수 없습니다."));


        EvidenceEntity evidenceEntity = EvidenceEntity.builder()
                .user(user)
                .title(createEvidenceDTO.getTitle())
                .description(createEvidenceDTO.getDescription())
                .category(category)
                .build();

        evidenceEntity.setFileUrls(getUrls);

        evidenceRepository.save(evidenceEntity);
        return EvidenceResponseDTO.toDto(evidenceEntity);
    }

    public List<MonthEvidenceResponseDTO> findAll(String username, String year, String month) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));

        List<EvidenceEntity> evidenceEntityList = evidenceRepository.findByUserAndYearAndMonth(user, Integer.parseInt(year), Integer.parseInt(month));

        List<EvidenceResponseDTO> evidenceResponseDTOS = new ArrayList<>();

        // 날짜 별로 그룹핑
        Map<LocalDate, Long> groupedByDay = evidenceEntityList.stream()
                .collect(Collectors.groupingBy(
                        evidence -> evidence.getCreatedAt().toLocalDate(),
                        Collectors.counting()
                ));

        List<MonthEvidenceResponseDTO> responseDTOs = new ArrayList<>();
        for (Map.Entry<LocalDate, Long> entry : groupedByDay.entrySet()) {
            responseDTOs.add(MonthEvidenceResponseDTO.builder()
                    .created_at(LocalDate.from(entry.getKey().atStartOfDay()))
                    .evidenceCount(entry.getValue().intValue())
                    .build());
        }

        return responseDTOs;
    }

    public List<EvidenceResponseDTO> findEvidenceByCategory(String username, CategoryTypeEnum name) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));

        CategoryEntity category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("해당 카테고리 이름을 찾을 수 없습니다."));

        List<EvidenceEntity> evidenceEntityList = evidenceRepository.findAllByUserAndCategory(user, category);
        List<EvidenceResponseDTO> evidenceResponseDTOS = new ArrayList<>();
        evidenceEntityList.forEach(entity -> {
            try {
                evidenceResponseDTOS.add(EvidenceResponseDTO.toDto(entity));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return evidenceResponseDTOS;
    }


    public void deleteEvidence(Integer id) throws JsonProcessingException {
        EvidenceEntity evidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new EvidenceNotFoundException("증거를 찾을 수 없습니다."));

        evidenceRepository.delete(evidence);

        String prefix = "https://poksin.s3.ap-northeast-2.amazonaws.com/";

        for (String fileUrl : evidence.getFileUrls()) {
            String fileName = removePrefix(fileUrl, prefix);
            System.out.println(fileName);
            s3Uploader.deleteFile(fileName);
        }
    }

    private String removePrefix(String url, String prefix) {
        if (url.startsWith(prefix)) {
            return url.replace(prefix, "");
        }
        return url;
    }
}
