package io.github.sevenguard.applications.controller;

import io.github.sevenguard.applications.dto.StockMovementDTO;
import io.github.sevenguard.applications.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    // Listar todas as movimentações com paginação
    @GetMapping
    public ResponseEntity<Page<StockMovementDTO>> getAllStockMovements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(stockMovementService.findAll(PageRequest.of(page, size)));
    }

    // Buscar movimentação por ID
    @GetMapping("/{id}")
    public ResponseEntity<StockMovementDTO> getStockMovementById(@PathVariable Long id) {
        return ResponseEntity.ok(stockMovementService.findById(id));
    }

    // Criar uma nova movimentação de estoque
    @PostMapping
    public ResponseEntity<StockMovementDTO> createStockMovement(@Valid @RequestBody StockMovementDTO stockMovementDTO) {
        return ResponseEntity.status(201).body(stockMovementService.create(stockMovementDTO));
    }

    // Atualizar uma movimentação de estoque existente
    @PutMapping("/{id}")
    public ResponseEntity<StockMovementDTO> updateStockMovement(@PathVariable Long id, @Valid @RequestBody StockMovementDTO stockMovementDTO) {
        return ResponseEntity.ok(stockMovementService.update(id, stockMovementDTO));
    }

    // Excluir logicamente uma movimentação de estoque
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
        stockMovementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar movimentações por ID do produto
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovementDTO>> getStockMovementsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(stockMovementService.findByProductId(productId));
    }

    // Buscar movimentações por tipo (ENTRY ou EXIT)
    @GetMapping("/type/{movementType}")
    public ResponseEntity<List<StockMovementDTO>> getStockMovementsByType(@PathVariable String movementType) {
        return ResponseEntity.ok(stockMovementService.findByMovementType(movementType));
    }

    // Buscar movimentações por intervalo de datas
    @GetMapping("/date-range")
    public ResponseEntity<List<StockMovementDTO>> getStockMovementsByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return ResponseEntity.ok(stockMovementService.findByMovementDateRange(startDate, endDate));
    }

    // Calcular o estoque total de um produto
    @GetMapping("/product/{productId}/stock")
    public ResponseEntity<Integer> calculateStockForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockMovementService.calculateStockForProduct(productId));
    }
}
