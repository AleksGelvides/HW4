package org.example.hw4.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.hw4.exceptions.EntityNotFoundException;
import org.example.hw4.mappers.comment.CommentMapper;
import org.example.hw4.repository.CommentRepository;
import org.example.hw4.repository.data.Comment;
import org.example.hw4.repository.data.User;
import org.example.hw4.service.api.CommentService;
import org.example.hw4.web.dto.request.comment.CommentChangeRequest;
import org.example.hw4.web.dto.response.CommentDto;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @SneakyThrows
    public CommentDto saveOrUpdate(CommentChangeRequest request) {
        Comment comment = commentMapper.requestToEntity(request);
        if (Objects.isNull(comment.getId())) {
            var newComment = commentRepository.save(comment);
            return commentMapper.toDto(newComment);
        }
        var oldComment = commentRepository.findById(comment.getId()).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Комментарий с id: {0} не найден", comment.getId())));
        oldComment = commentRepository.save(commentMapper.merge(oldComment, comment));
        return commentMapper.toDto(oldComment);
    }

    @Override
    @SneakyThrows
    public CommentDto findById(Long id) {
        return commentMapper.toDto(
                commentRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(MessageFormat.format("Комментарий с id: {0} не найден", id))));
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }
}
