package io.github.sevenguard.applications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.sevenguard.domain.enums.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StockMovementDTO {

    @JsonProperty(value = "id",access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(value = "product_id")
    @NotNull(message = "Product ID is required")
    private Long productId;

    @JsonProperty(value = "quantity")
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @JsonProperty(value = "movement_type")
    @NotNull(message = "Movement type is required")
    private MovementType movementType;

    @JsonProperty(value = "movement_date")
    @NotNull(message = "Movement date is required")
    private LocalDate movementDate;
}
