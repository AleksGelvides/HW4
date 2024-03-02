package org.example.hw4.repository.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "USERS")
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private String login;
    @OneToMany(mappedBy = BaseEntity.Fields.id)
    private Set<Comment> comments;
    @OneToMany(mappedBy = BaseEntity.Fields.id)
    private Set<News> news;
}
