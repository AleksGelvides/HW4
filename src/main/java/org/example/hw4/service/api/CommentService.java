package org.example.hw4.service.api;

import org.apache.commons.lang3.NotImplementedException;
import org.example.hw4.web.dto.PageResponse;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.comment.CommentChangeRequest;
import org.example.hw4.web.dto.response.CommentDto;


public interface CommentService extends HwService<CommentDto>{

    @Override
    default PageResponse<Object> findByCriteria(SearchCriteria request) {
        throw new NotImplementedException();
    }

    CommentDto saveOrUpdate(CommentChangeRequest request);
}
