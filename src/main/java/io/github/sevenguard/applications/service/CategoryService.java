package io.github.sevenguard.applications.service;

import io.github.sevenguard.applications.dto.CategoryDTO;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll(String name, Pageable pageable);
    CategoryDTO findById(Long id);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(Long id, CategoryDTO categoryDTO);
    void delete(Long id);

}
