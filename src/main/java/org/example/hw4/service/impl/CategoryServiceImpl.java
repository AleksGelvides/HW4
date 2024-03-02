package org.example.hw4.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.hw4.exceptions.EntityNotFoundException;
import org.example.hw4.mappers.category.CategoryMapper;
import org.example.hw4.repository.CategoryRepository;
import org.example.hw4.repository.data.Category;
import org.example.hw4.repository.specificationBuilder.SearchCategorySpecificationBuilder;
import org.example.hw4.service.api.CategoryService;
import org.example.hw4.web.dto.PageResponse;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.category.CategoryChangeRequest;
import org.example.hw4.web.dto.response.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SearchCategorySpecificationBuilder specificationBuilder;
    @Override
    public PageResponse<Object> findByCriteria(SearchCriteria criteria) {
        Page<Category> categories = categoryRepository
                .findAll(specificationBuilder.buildByCriteria(criteria),
                        PageRequest.of(criteria.getPage(), criteria.getPageSize()));
        return PageResponse.builder()
                .body(categories.get().map(categoryMapper::toDto).collect(Collectors.toList()))
                .page(categories.getNumber())
                .size(categories.getContent().size())
                .total(categories.getTotalElements()).build();
    }

    @Override
    @SneakyThrows
    public CategoryDto findById(Long id) {
        Category category = categoryRepository
                .findById(id).orElseThrow(
                        () -> new EntityNotFoundException(MessageFormat.format("Категория с id: {0} не найдена", id)));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    @SneakyThrows
    public CategoryDto saveOrUpdate(CategoryChangeRequest categoryChangeRequest) {
        Category category = categoryMapper.requestToEntity(categoryChangeRequest);
        if (Objects.isNull(category.getId())) {
            var newCategory = categoryRepository.save(category);
            return categoryMapper.toDto(newCategory);
        }
        var oldCategory = categoryRepository.findById(category.getId()).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Категория с id: {0} не найдена", category.getId())));
        oldCategory = categoryRepository.save(categoryMapper.merge(oldCategory, category));
        return categoryMapper.toDto(oldCategory);
    }
}
