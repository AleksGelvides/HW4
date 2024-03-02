package org.example.hw4.service.api;

import org.apache.commons.lang3.NotImplementedException;
import org.example.hw4.web.dto.request.category.CategoryChangeRequest;
import org.example.hw4.web.dto.response.CategoryDto;

public interface CategoryService extends HwService<CategoryDto>{

    CategoryDto saveOrUpdate(CategoryChangeRequest request);
    @Override
    default void remove(Long id) {
        throw new NotImplementedException();
    }
}
