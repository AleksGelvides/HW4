package org.example.hw4.mappers.user;

import org.example.hw4.mappers.BaseMapper;
import org.example.hw4.mappers.comment.CommentMapper;
import org.example.hw4.mappers.news.NewsMapper;
import org.example.hw4.repository.data.User;
import org.example.hw4.web.dto.request.user.UserChangeRequest;
import org.example.hw4.web.dto.response.UserDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class,
        uses = {CommentMapper.class, NewsMapper.class})
public interface UserMapper extends BaseMapper<User, UserDto, UserChangeRequest> {

}
