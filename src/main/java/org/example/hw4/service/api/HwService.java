package org.example.hw4.service.api;

import org.example.hw4.web.dto.PageResponse;
import org.example.hw4.web.dto.request.SearchCriteria;

public interface HwService<DTO> {
    PageResponse<Object> findByCriteria(SearchCriteria request);


    DTO findById(Long id);

    void remove(Long id);
}
