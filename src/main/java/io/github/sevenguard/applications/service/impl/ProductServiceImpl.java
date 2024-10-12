package io.github.sevenguard.applications.service.impl;

import io.github.sevenguard.applications.dto.ProductDTO;
import io.github.sevenguard.applications.service.ProductService;
import io.github.sevenguard.domain.models.Product;
import io.github.sevenguard.domain.models.Category;
import io.github.sevenguard.infrastructure.repository.ProductRepository;
import io.github.sevenguard.infrastructure.repository.CategoryRepository;
import io.github.sevenguard.infrastructure.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<ProductDTO> findAll(String name, Pageable pageable) {
        return productRepository.findAll(name, pageable)
                .map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = getProduct(id);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Category category = getCategory(productDTO);

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = getProduct(id);

        Category category = getCategory(productDTO);

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public void delete(Long id) {
        Product product = getProduct(id);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    private Category getCategory(ProductDTO productDTO) {
        return categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + productDTO.getCategoryId()));
    }
}
