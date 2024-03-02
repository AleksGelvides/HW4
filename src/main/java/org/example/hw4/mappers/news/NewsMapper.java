package org.example.hw4.mappers.news;

import org.example.hw4.mappers.BaseMapper;
import org.example.hw4.mappers.category.CategoryMapper;
import org.example.hw4.mappers.comment.CommentMapper;
import org.example.hw4.mappers.user.UserMapper;
import org.example.hw4.repository.data.News;
import org.example.hw4.web.dto.request.news.NewsChangeRequest;
import org.example.hw4.web.dto.response.NewsDto;
import org.example.hw4.web.dto.response.NewsPreviewDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = BaseMapper.class,
        uses = {UserMapper.class, CommentMapper.class, CategoryMapper.class})
public interface NewsMapper extends BaseMapper<News, NewsDto, NewsChangeRequest> {

    @Override
    NewsDto toDto(News news);
    NewsPreviewDto toPreviewDto(News news);
}
