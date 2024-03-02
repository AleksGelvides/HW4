package org.example.hw4.web.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.hw4.web.dto.response.UserDto;

@Getter
@Setter
public class CommentChangeRequest {
    @Schema(description = "ID комментария. Не обязателен при создании категории")
    private Long id;
    @Schema(description = "Текст комментария")
    @NotBlank(message = "Текст комментария должен быть заполнен")
    @Size(min = 3, max = 250, message = "Текст комментария не может быть меньше {min} и больше {max}")
    private String text;
    @Schema(description = "Уникальный идентификатор новости")
    @NotBlank(message = "newsId обязательное поле")
    private Long newsId;
    @Schema(description = "Уникальный идентификатор автора комментария")
    @NotBlank(message = "authorId обязательное поле")
    private Long authorId;
}
