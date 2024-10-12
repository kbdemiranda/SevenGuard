package io.github.sevenguard.applications.service;

import io.github.sevenguard.applications.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDTO> findAll(String name, Pageable pageable);
    ProductDTO findById(Long id);
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(Long id, ProductDTO productDTO);
    void delete(Long id);
}
