package io.github.sevenguard.infrastructure.repository;

import io.github.sevenguard.domain.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Consulta com filtro case-insensitive para nome de produto
    @Query("SELECT p FROM Product p WHERE (:name IS NULL OR p.name ILIKE %:name%) AND p.deletedAt IS NULL")
    Page<Product> findAll(@Param("name") String name, Pageable pageable);
}
