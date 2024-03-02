package org.example.hw4.web.dto.request.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.aspectj.lang.JoinPoint;
import org.example.hw4.web.dto.request.SearchCriteria;

@Getter
@Setter
@Builder
public class NewsChangeRequest {
    @Schema(description = "ID новости. Не обязателен при создании категории")
    private Long id;
    @Schema(description = "Заголовок новости")
    @NotBlank(message = "Заголовок новости должен быть заполнен")
    @Size(min = 3, max = 50, message = "Заголовок новости не может быть меньше {min} и больше {max}")
    private String header;
    @Schema(description = "Текст новости")
    @NotBlank(message = "Новость должна быть заполнена")
    @Size(min = 3, message = "Новость не может быть меньше {min}")
    private String text;
    @Schema(description = "Уникальный идентификатор автора новости")
    @NotBlank(message = "authorId должен быть заполнен")
    private Long authorId;
    @Schema(description = "Уникальный идентификатор категории новости")
    @NotBlank(message = "categoryId должен быть заполнен")
    private Long categoryId;
}
