package org.example.hw4.repository;

import org.example.hw4.repository.data.News;
import org.example.hw4.repository.specificationBuilder.SpecificationBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
}
