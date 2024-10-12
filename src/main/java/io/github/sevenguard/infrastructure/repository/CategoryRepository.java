package io.github.sevenguard.infrastructure.repository;

import io.github.sevenguard.domain.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE (:name IS NULL OR c.name ILIKE %:name%) AND c.deletedAt IS NULL")
    Page<Category> findAll(@Param("name") String name, Pageable pageable);

}
