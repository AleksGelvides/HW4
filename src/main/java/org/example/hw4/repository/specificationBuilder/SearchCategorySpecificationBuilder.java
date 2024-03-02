package org.example.hw4.repository.specificationBuilder;

import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.example.hw4.repository.data.Category;
import org.example.hw4.repository.data.User;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class SearchCategorySpecificationBuilder implements SpecificationBuilder<Category> {

    @Override
    public Specification<Category> buildByCriteria(SearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {

            if (Objects.isNull(criteria)) return null;

            List<Predicate> predicates = new LinkedList<>();

            if (StringUtils.isNotEmpty(criteria.getSearchWord())) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(Category.Fields.name)),
                                MessageFormat.format("%{0}%", criteria.getSearchWord()).toLowerCase())
                );
            }

            addDateFilterPredicate(predicates, criteriaBuilder, root, criteria);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
