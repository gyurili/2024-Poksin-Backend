package com.viewmore.poksin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<EvidenceEntity> evidences = new ArrayList<>();
}
