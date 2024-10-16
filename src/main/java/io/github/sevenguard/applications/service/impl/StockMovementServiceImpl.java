package io.github.sevenguard.applications.service.impl;

import io.github.sevenguard.applications.dto.StockMovementDTO;
import io.github.sevenguard.applications.service.StockMovementService;
import io.github.sevenguard.domain.enums.MovementType;
import io.github.sevenguard.domain.models.Product;
import io.github.sevenguard.domain.models.StockMovement;
import io.github.sevenguard.infrastructure.repository.StockMovementRepository;
import io.github.sevenguard.infrastructure.repository.ProductRepository;
import io.github.sevenguard.infrastructure.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<StockMovementDTO> findAll(Pageable pageable) {
        return stockMovementRepository.findAllNonDeleted(pageable)
                .map(movement -> modelMapper.map(movement, StockMovementDTO.class));
    }

    @Override
    public StockMovementDTO findById(Long id) {
        StockMovement stockMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock movement not found with id " + id));
        return modelMapper.map(stockMovement, StockMovementDTO.class);
    }

    @Override
    public StockMovementDTO create(StockMovementDTO stockMovementDTO) {
        Product product = productRepository.findById(stockMovementDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + stockMovementDTO.getProductId()));

        StockMovement stockMovement = modelMapper.map(stockMovementDTO, StockMovement.class);
        stockMovement.setProduct(product);

        // Se for saída, validar se há estoque suficiente
        if (stockMovement.getMovementType() == MovementType.EXIT) {
            Integer totalStock = calculateStockForProduct(product.getId());
            if (totalStock == null || totalStock < stockMovement.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock available for product ID " + product.getId());
            }
        }

        StockMovement savedMovement = stockMovementRepository.save(stockMovement);
        return modelMapper.map(savedMovement, StockMovementDTO.class);
    }

    @Override
    public StockMovementDTO update(Long id, StockMovementDTO stockMovementDTO) {
        StockMovement stockMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock movement not found with id " + id));

        Product product = productRepository.findById(stockMovementDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + stockMovementDTO.getProductId()));

        stockMovement.setProduct(product);
        stockMovement.setQuantity(stockMovementDTO.getQuantity());
        stockMovement.setMovementType(stockMovementDTO.getMovementType());
        stockMovement.setMovementDate(stockMovementDTO.getMovementDate());
        stockMovement.setUpdatedAt(LocalDateTime.now());

        StockMovement updatedMovement = stockMovementRepository.save(stockMovement);
        return modelMapper.map(updatedMovement, StockMovementDTO.class);
    }

    @Override
    public void delete(Long id) {
        StockMovement stockMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock movement not found with id " + id));
        stockMovement.setDeletedAt(LocalDateTime.now()); // Exclusão lógica
        stockMovementRepository.save(stockMovement);
    }

    @Override
    public List<StockMovementDTO> findByProductId(Long productId) {
        return stockMovementRepository.findByProductId(productId)
                .stream()
                .map(movement -> modelMapper.map(movement, StockMovementDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StockMovementDTO> findByMovementType(String movementType) {
        MovementType type = MovementType.valueOf(movementType.toUpperCase());
        return stockMovementRepository.findByMovementType(type)
                .stream()
                .map(movement -> modelMapper.map(movement, StockMovementDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer calculateStockForProduct(Long productId) {
        Integer totalEntries = stockMovementRepository.sumQuantityByProductAndMovementType(productId, MovementType.ENTRY);
        Integer totalExits = stockMovementRepository.sumQuantityByProductAndMovementType(productId, MovementType.EXIT);
        if (totalEntries == null) totalEntries = 0;
        if (totalExits == null) totalExits = 0;
        return totalEntries - totalExits;
    }

    @Override
    public List<StockMovementDTO> findByMovementDateRange(LocalDate startDate, LocalDate endDate) {
        return stockMovementRepository.findByMovementDateBetween(startDate, endDate)
                .stream()
                .map(movement -> modelMapper.map(movement, StockMovementDTO.class))
                .collect(Collectors.toList());
    }
}
