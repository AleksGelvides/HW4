package org.example.hw4.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hw4.aop.HwSecurity;
import org.example.hw4.exceptions.CommonHWServiceException;
import org.example.hw4.service.api.CommentService;
import org.example.hw4.web.dto.request.comment.CommentChangeRequest;
import org.example.hw4.web.dto.response.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Tag(name = "Comments v1", description = "API для взаимодействия с контроллером управления комментариями")
public class CommentController {
    private final CommentService commentServ;

    @Operation(
            summary = "Создать комментарий",
            description = "id в теле запроса является НЕ обязательным параметром",
            tags = {"comment"}
    )
    @PostMapping
    @HwSecurity
    public ResponseEntity<CommentDto> save(@RequestBody @Valid CommentChangeRequest request, HttpServletRequest httpServletRequest) {
        request.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentServ.saveOrUpdate(request));
    }

    @Operation(
            summary = "Редактировать комментарий",
            description = "id в теле запроса является обязательным параметром",
            tags = {"comment"}
    )
    @PutMapping
    @HwSecurity
    public ResponseEntity<CommentDto> edit(@RequestBody @Valid CommentChangeRequest request, HttpServletRequest httpServletRequest) throws CommonHWServiceException {
        if (Objects.isNull(request.getId()))
            throw new CommonHWServiceException("Для редактирования записи должен быть указан ID записи");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentServ.saveOrUpdate(request));
    }

    @Operation(
            summary = "Удалить комментарий по id",
            tags = {"comment"}
    )
    @DeleteMapping
    @HwSecurity
    public ResponseEntity<Void> delete(Long id, HttpServletRequest httpServletRequest) {
        commentServ.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
