package org.example.hw4.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hw4.aop.HwSecurity;
import org.example.hw4.exceptions.CommonHWServiceException;
import org.example.hw4.service.api.UserService;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.user.UserChangeRequest;
import org.example.hw4.web.dto.response.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User v1", description = "API для взаимодействия с контроллером управления пользователями")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Поиск пользователей по параметрам",
            description = "Параметры page и pageSize не обязательны.\n" +
                    "Без указания параметров вернется 0 страница с 10 пользователями",
            tags = {"user"}
    )
    @GetMapping("/search")
    public ResponseEntity<Object> getAll(SearchCriteria criteria) {
        return ResponseEntity.ok(userService.findByCriteria(criteria));
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "id в теле запроса является НЕ обязательным параметром",
            tags = {"user"}
    )
    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserChangeRequest userChangeRequest,
                                        HttpServletRequest httpServletRequest,
                                        BindingResult bindingResult) {
        userChangeRequest.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.saveOrUpdate(userChangeRequest));
    }

    @Operation(
            summary = "Редактировать пользователя",
            description = "id в теле запроса является обязательным параметром",
            tags = {"user"}
    )
    @PutMapping
    @HwSecurity
    public ResponseEntity<UserDto> edit(@RequestBody @Valid UserChangeRequest userChangeRequest, HttpServletRequest httpServletRequest) throws CommonHWServiceException {
        if (Objects.isNull(userChangeRequest.getId()))
            throw new CommonHWServiceException("Для редактирования записи должен быть указан ID записи");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.saveOrUpdate(userChangeRequest));
    }

    @Operation(
            summary = "Удалить пользователя по id",
            tags = {"user"}
    )
    @DeleteMapping
    @HwSecurity
    public ResponseEntity<Void> delete(Long id, HttpServletRequest httpServletRequest) {
        userService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
