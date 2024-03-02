package org.example.hw4.web.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserChangeRequest {
    @Schema(description = "ID пользователя. Не обязателен при создании пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    @NotBlank(message = "Имя пользователя должна быть заполнено")
    @Size(min = 2, max = 10, message = "Имя пользователя не может быть меньше {min} и больше {max}")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    @NotBlank(message = "Фамилия пользователя должна быть заполнено")
    @Size(min = 2, max = 10, message = "Фамилия пользователя не может быть меньше {min} и больше {max}")
    private String lastName;
    @Schema(description = "Логин пользователя")
    @NotBlank(message = "Логин пользователя должна быть заполнено")
    @Size(min = 5, max = 20, message = "Логин пользователя не может быть меньше {min} и больше {max}")
    private String login;
}
