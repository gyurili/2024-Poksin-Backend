package com.viewmore.poksin.repository;

import com.viewmore.poksin.entity.ViolenceSegmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ViolenceSegmentRepository extends JpaRepository<ViolenceSegmentEntity, Integer> {
    public Optional<List<ViolenceSegmentEntity>> findAllByEvidence_Id(int evidence_id);
    public Integer countAllByEvidence_Id(int evidence_id);
    @Query("SELECT SUM(v.duration) FROM ViolenceSegmentEntity v WHERE v.evidence.id = :evidenceId")
    Float sumDurationByEvidence_Id(@Param("evidenceId") int evidenceId);
}
