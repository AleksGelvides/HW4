package org.example.hw4.service.impl;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.hw4.exceptions.EntityNotFoundException;
import org.example.hw4.mappers.news.NewsMapper;
import org.example.hw4.repository.CategoryRepository;
import org.example.hw4.repository.NewsRepository;
import org.example.hw4.repository.UserRepository;
import org.example.hw4.repository.data.Category;
import org.example.hw4.repository.data.News;
import org.example.hw4.repository.data.User;
import org.example.hw4.repository.specificationBuilder.SearchNewsSpecificationBuilder;
import org.example.hw4.service.api.NewsService;
import org.example.hw4.web.dto.PageResponse;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.news.NewsChangeRequest;
import org.example.hw4.web.dto.response.NewsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final NewsMapper newsMapper;
    private final SearchNewsSpecificationBuilder specificationBuilder;

    @Override
    public PageResponse<Object> findByCriteria(SearchCriteria criteria) {
        Page<News> categories = newsRepository
                .findAll(specificationBuilder.buildByCriteria(criteria),
                        PageRequest.of(criteria.getPage(), criteria.getPageSize()));
        return PageResponse.builder()
                .body(categories.get().map(newsMapper::toPreviewDto).collect(Collectors.toList()))
                .page(categories.getNumber())
                .size(categories.getContent().size())
                .total(categories.getTotalElements()).build();
    }

    @Override
    @SneakyThrows
    public NewsDto findById(Long id) {
        News news = newsRepository
                .findById(id).orElseThrow(
                        () -> new EntityNotFoundException(MessageFormat.format("Новость с id: {0} не найдена", id)));
        return newsMapper.toDto(news);
    }

    @Override
    public void remove(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    @Transactional
    public NewsDto saveOrUpdate(NewsChangeRequest request) {
        News news = newsMapper.requestToEntity(request);
        if (Objects.isNull(news.getId())) {
            User user = userRepository.findById(request.getAuthorId()).orElseThrow(
                    () -> new EntityNotFoundException(MessageFormat.format("Автор с id: {0} не найден", news.getId())));
            news.setAuthor(user);
            user.getNews().add(news);
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                    () -> new EntityNotFoundException(MessageFormat.format("Категория с id: {0} не найдена", news.getId())));
            news.setCategory(category);
            category.getNews().add(news);
            var newNews = newsRepository.save(news);
            return newsMapper.toDto(newNews);
        }
        var oldCategory = newsRepository.findById(news.getId()).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Новость с id: {0} не найдена", news.getId())));
        oldCategory = newsRepository.save(newsMapper.merge(oldCategory, news));
        return newsMapper.toDto(oldCategory);
    }
}
