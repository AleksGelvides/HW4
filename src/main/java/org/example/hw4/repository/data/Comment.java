package org.example.hw4.repository.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class Comment extends BaseEntity {
    private String text;
    @ManyToOne
    private User author;
    @ManyToOne
    private News news;
}
