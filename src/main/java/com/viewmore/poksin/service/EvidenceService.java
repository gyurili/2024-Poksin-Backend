package com.viewmore.poksin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viewmore.poksin.dto.evidence.CreateEvidenceDTO;
import com.viewmore.poksin.dto.evidence.EvidenceResponseDTO;
import com.viewmore.poksin.entity.CategoryEntity;
import com.viewmore.poksin.entity.CategoryTypeEnum;
import com.viewmore.poksin.entity.EvidenceEntity;
import com.viewmore.poksin.entity.UserEntity;
import com.viewmore.poksin.repository.CategoryRepository;
import com.viewmore.poksin.repository.EvidenceRepository;
import com.viewmore.poksin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));


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

    public List<EvidenceResponseDTO> findAll(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: " + username));

        List<EvidenceEntity> evidenceEntityList = evidenceRepository.findAllByUser(user);

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
}
