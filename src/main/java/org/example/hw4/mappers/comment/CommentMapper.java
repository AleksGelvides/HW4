package org.example.hw4.mappers.comment;

import org.example.hw4.mappers.BaseMapper;
import org.example.hw4.mappers.news.NewsMapper;
import org.example.hw4.mappers.user.UserMapper;
import org.example.hw4.repository.data.Comment;
import org.example.hw4.web.dto.request.comment.CommentChangeRequest;
import org.example.hw4.web.dto.response.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapper.class,
        uses = {UserMapper.class, NewsMapper.class})
public interface CommentMapper extends BaseMapper<Comment, CommentDto, CommentChangeRequest> {

    @Override
    @Mapping(target = "newsId", source = "news.id")
    CommentDto toDto(Comment comment);
}
