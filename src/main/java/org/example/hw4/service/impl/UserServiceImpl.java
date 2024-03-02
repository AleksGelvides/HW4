package org.example.hw4.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.hw4.exceptions.EntityNotFoundException;
import org.example.hw4.mappers.user.UserMapper;
import org.example.hw4.repository.CategoryRepository;
import org.example.hw4.repository.UserRepository;
import org.example.hw4.repository.data.User;
import org.example.hw4.repository.specificationBuilder.SearchUserSpecificationBuilder;
import org.example.hw4.service.api.UserService;
import org.example.hw4.web.dto.PageResponse;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.example.hw4.web.dto.request.user.UserChangeRequest;
import org.example.hw4.web.dto.response.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SearchUserSpecificationBuilder searchUserSpecificationBuilder;
    @Override
    public PageResponse<Object> findByCriteria(SearchCriteria criteria) {
        var userPage = userRepository.findAll(searchUserSpecificationBuilder.buildByCriteria(criteria),
                PageRequest.of(criteria.getPage(), criteria.getPageSize()));
        return PageResponse.builder()
                .body(userPage.get().map(userMapper::toDto).collect(Collectors.toList()))
                .page(userPage.getNumber())
                .size(userPage.getContent().size())
                .total(userPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    @SneakyThrows
    public UserDto saveOrUpdate(UserChangeRequest userChangeRequest) {
        User user = userMapper.requestToEntity(userChangeRequest);
        if (Objects.isNull(user.getId())) {
            var newUser = userRepository.save(user);
            return userMapper.toDto(newUser);
        }
        var oldUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с id: {0} не найден", user.getId())));
        oldUser = userRepository.save(userMapper.merge(oldUser, user));
        return userMapper.toDto(oldUser);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public UserDto findById(Long id) {
        var result = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Пользователь с id: {0} не найден", id)));
        return userMapper.toDto(result);
    }
}
