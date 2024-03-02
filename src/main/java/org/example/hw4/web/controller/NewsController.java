package org.example.hw4.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hw4.aop.HwSecurity;
import org.example.hw4.exceptions.CommonHWServiceException;
import org.example.hw4.service.api.NewsService;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.news.NewsChangeRequest;
import org.example.hw4.web.dto.response.NewsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News v1", description = "API для взаимодействия с контроллером управления новостями")
public class NewsController {
    private final NewsService newsService;

    @Operation(
            summary = "Получить все новости по поисковому параметру",
            description = "Параметры page и pageSize не обязательны.\n" +
                    "Без указания параметров вернется 0 страница с 10 новостями",
            tags = {"news"}
    )
    @GetMapping
    public ResponseEntity<Object> getAll(SearchCriteria criteria) {
        return ResponseEntity.ok(newsService.findByCriteria(criteria));
    }

    @Operation(
            summary = "Получить новость по id",
            description = "Получить новость по id",
            tags = {"news"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.findById(id));
    }

    @Operation(
            summary = "Создать новость",
            description = "id в теле запроса является НЕ обязательным параметром",
            tags = {"news"}
    )
    @PostMapping
    @HwSecurity
    public ResponseEntity<NewsDto> save(@RequestBody @Valid NewsChangeRequest request, HttpServletRequest httpServletRequest) {
        request.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsService.saveOrUpdate(request));
    }

    @Operation(
            summary = "Редактировать новость",
            description = "id в теле запроса является обязательным параметром",
            tags = {"news"}
    )
    @PutMapping
    @HwSecurity
    public ResponseEntity<NewsDto> edit(@RequestBody @Valid NewsChangeRequest request, HttpServletRequest httpServletRequest) throws CommonHWServiceException {
        if (Objects.isNull(request.getId()))
            throw new CommonHWServiceException("Для редактирования записи должен быть указан ID записи");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsService.saveOrUpdate(request));
    }

    @Operation(
            summary = "Удалить новость по id",
            tags = {"news"}
    )
    @DeleteMapping
    @HwSecurity
    public ResponseEntity<Void> delete(Long id, HttpServletRequest httpServletRequest) {
        newsService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
