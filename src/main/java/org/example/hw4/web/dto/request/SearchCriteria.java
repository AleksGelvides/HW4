package org.example.hw4.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class SearchCriteria {
    @Schema(description = "Номер страницы с записями. Начинается с 0")
    private Integer page;
    @Schema(description = "Размер страницы. По дефолту размер = 10")
    private Integer pageSize;
    @Schema(description = "Поисковое слово")
    private String searchWord;
    @Schema(description = "Пользователь зарегистрирован ОТ")
    private LocalDate createFrom;
    @Schema(description = "Пользователь зарегистрирован ДО")
    private LocalDate createTo;

    public Integer getPage() {
        return Objects.nonNull(this.page) ? this.page : 0;
    }

    public Integer getPageSize() {
        return Objects.nonNull(this.pageSize) && this.page != 0 ? this.pageSize : 10;
    }
}
