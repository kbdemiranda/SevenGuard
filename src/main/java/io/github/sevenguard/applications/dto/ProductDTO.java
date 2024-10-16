package io.github.sevenguard.applications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "name")
    @NotBlank(message = "Product name is required")
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "price")
    @NotNull(message = "Product price is required")
    private BigDecimal price;
    @JsonProperty(value = "quantity_in_stock")
    @NotNull(message = "Product quantity in stock is required")
    private Integer quantityInStock;
    @JsonProperty(value = "category_id")
    @NotNull(message = "Product category is required")
    private Long categoryId;
}
