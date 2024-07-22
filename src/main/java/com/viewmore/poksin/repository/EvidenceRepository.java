package com.viewmore.poksin.repository;

import com.viewmore.poksin.entity.CategoryEntity;
import com.viewmore.poksin.entity.EvidenceEntity;
import com.viewmore.poksin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenceRepository extends JpaRepository<EvidenceEntity, Integer> {
    List<EvidenceEntity> findAllByUser(UserEntity user);
    List<EvidenceEntity> findAllByUserAndCategory(UserEntity user, CategoryEntity category);
}
