package io.github.sevenguard.applications.service;

import io.github.sevenguard.applications.dto.StockMovementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StockMovementService {

    Page<StockMovementDTO> findAll(Pageable pageable);
    StockMovementDTO findById(Long id);
    StockMovementDTO create(StockMovementDTO stockMovementDTO);
    StockMovementDTO update(Long id, StockMovementDTO stockMovementDTO);
    void delete(Long id);
    List<StockMovementDTO> findByProductId(Long productId);
    List<StockMovementDTO> findByMovementType(String movementType);
    Integer calculateStockForProduct(Long productId);
    List<StockMovementDTO> findByMovementDateRange(LocalDate startDate, LocalDate endDate);
}

