package org.example.hw4.web.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CategoryChangeRequest {
    @Schema(description = "ID категории. Не обязателен при создании категории")
    private Long id;
    @Schema(description = "Наименование категории")
    @NotBlank(message = "Категория должна быть заполнено")
    @Size(min = 3, max = 30, message = "Категория не может быть меньше {min} и больше {max}")
    private String name;
}
