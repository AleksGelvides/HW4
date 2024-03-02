package org.example.hw4.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.example.hw4.repository.data.Category;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDto {
    private Long id;
    private String text;
    private CategoryDto category;
    private UserDto author;
    private Set<CommentDto> comments;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
