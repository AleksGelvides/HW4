package org.example.hw4.repository;

import org.example.hw4.repository.data.Comment;
import org.example.hw4.repository.data.News;
import org.example.hw4.repository.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
