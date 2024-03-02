package org.example.hw4.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
