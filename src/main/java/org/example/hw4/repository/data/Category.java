package org.example.hw4.repository.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Formula;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "CATEGORIES")
public class Category extends BaseEntity{
    @Column(name = "category_name")
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<News> news;
    @Formula("(SELECT COUNT(*) FROM NEWS n WHERE n.CATEGORY_ID = ID)")
    private Integer newsCount;
}
