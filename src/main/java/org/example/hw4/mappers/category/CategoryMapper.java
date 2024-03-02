package org.example.hw4.mappers.category;

import org.example.hw4.mappers.BaseMapper;
import org.example.hw4.repository.data.Category;
import org.example.hw4.web.dto.request.category.CategoryChangeRequest;
import org.example.hw4.web.dto.response.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface CategoryMapper extends BaseMapper<Category, CategoryDto, CategoryChangeRequest> {
}
