package org.example.hw4.service.api;

import org.example.hw4.web.dto.request.news.NewsChangeRequest;
import org.example.hw4.web.dto.response.NewsDto;

public interface NewsService extends HwService<NewsDto>{

    NewsDto saveOrUpdate(NewsChangeRequest request);

}
