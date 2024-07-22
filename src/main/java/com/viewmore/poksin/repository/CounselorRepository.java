package com.viewmore.poksin.repository;

import com.viewmore.poksin.entity.CounselorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounselorRepository extends JpaRepository<CounselorEntity, Integer> {
    Optional<CounselorEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
