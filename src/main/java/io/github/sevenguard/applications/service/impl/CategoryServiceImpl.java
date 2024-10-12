package io.github.sevenguard.applications.service.impl;

import io.github.sevenguard.applications.dto.CategoryDTO;
import io.github.sevenguard.applications.service.CategoryService;
import io.github.sevenguard.domain.models.Category;
import io.github.sevenguard.infrastructure.repository.CategoryRepository;
import io.github.sevenguard.infrastructure.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll(String name, Pageable pageable) {
        return categoryRepository.findAll(name, pageable).stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = getCategory(id);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category category = getCategory(id);
        category.setName(categoryDTO.getName());
        category.setUpdatedAt(LocalDateTime.now());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void delete(Long id) {
        Category category = getCategory(id);
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }
}
