package org.example.hw4.repository.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Formula;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "NEWS")
public class News extends BaseEntity{

    private String header;
    private String text;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User author;
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments;
    @Formula("(SELECT COUNT(*) FROM COMMENTS c WHERE c.NEWS_ID = ID)")
    private Integer commentCount;
}
