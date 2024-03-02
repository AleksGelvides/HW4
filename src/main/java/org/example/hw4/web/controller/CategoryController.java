package org.example.hw4.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.hw4.exceptions.CommonHWServiceException;
import org.example.hw4.service.api.CategoryService;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.category.CategoryChangeRequest;
import org.example.hw4.web.dto.response.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Categories v1", description = "API для взаимодействия с контроллером управления категориями")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Поиск категории по параметрам",
            description = "Параметры page и pageSize не обязательны.\n" +
                    "Без указания параметров вернется 0 страница с 10 пользователями",
            tags = {"category"}
    )
    @GetMapping()
    public ResponseEntity<Object> getAll(SearchCriteria criteria) {
        return ResponseEntity.ok(categoryService.findByCriteria(criteria));
    }

    @Operation(
            summary = "Создать новой категории",
            description = "id в теле запроса является НЕ обязательным параметром",
            tags = {"category"}
    )
    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryChangeRequest categoryChangeRequest) {
        categoryChangeRequest.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.saveOrUpdate(categoryChangeRequest));
    }

    @Operation(
            summary = "Редактировать категорию",
            description = "id в теле запроса является обязательным параметром",
            tags = {"category"}
    )
    @PutMapping
    public ResponseEntity<CategoryDto> edit(@RequestBody @Valid CategoryChangeRequest categoryChangeRequest) throws CommonHWServiceException {
        if (Objects.isNull(categoryChangeRequest.getId()))
            throw new CommonHWServiceException("Для редактирования записи должен быть указан ID записи");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.saveOrUpdate(categoryChangeRequest));
    }
}
