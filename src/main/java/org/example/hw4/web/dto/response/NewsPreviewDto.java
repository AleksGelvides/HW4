package org.example.hw4.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsPreviewDto {
    private Long id;
    private String header;
    private String text;
    private CategoryDto category;
    private UserDto author;
    private Integer commentCount;
}
