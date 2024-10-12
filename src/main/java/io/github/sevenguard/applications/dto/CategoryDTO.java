package io.github.sevenguard.applications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;
}
