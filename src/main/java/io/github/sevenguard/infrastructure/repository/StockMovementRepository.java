package io.github.sevenguard.infrastructure.repository;

import io.github.sevenguard.domain.enums.MovementType;
import io.github.sevenguard.domain.models.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    // Buscar movimentações por produto
    List<StockMovement> findByProductId(Long productId);

    // Buscar movimentações por tipo
    List<StockMovement> findByMovementType(MovementType movementType);

    // Somar entradas ou saídas de um produto específico
    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm WHERE sm.product.id = :productId AND sm.movementType = :type")
    Integer sumQuantityByProductAndMovementType(@Param("productId") Long productId, @Param("type") MovementType movementType);

    // Buscar movimentações entre duas datas
    List<StockMovement> findByMovementDateBetween(LocalDate startDate, LocalDate endDate);

    // Buscar movimentações de um produto e tipo, paginadas
    Page<StockMovement> findByProductIdAndMovementType(Long productId, MovementType movementType, Pageable pageable);

    // Buscar todas as movimentações que não foram logicamente excluídas
    @Query("SELECT sm FROM StockMovement sm WHERE sm.deletedAt IS NULL")
    Page<StockMovement> findAllNonDeleted(Pageable pageable);
}

