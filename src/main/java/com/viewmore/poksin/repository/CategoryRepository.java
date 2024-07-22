package com.viewmore.poksin.repository;

import com.viewmore.poksin.entity.CategoryEntity;
import com.viewmore.poksin.entity.CategoryTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(CategoryTypeEnum type);
}
