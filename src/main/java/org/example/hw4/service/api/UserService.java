package org.example.hw4.service.api;

import org.example.hw4.web.dto.request.user.UserChangeRequest;
import org.example.hw4.web.dto.response.UserDto;

public interface UserService extends HwService<UserDto> {
    UserDto saveOrUpdate(UserChangeRequest userChangeRequest);
}
