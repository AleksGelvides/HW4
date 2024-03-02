package org.example.hw4.repository.specificationBuilder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.hw4.repository.data.BaseEntity;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

public interface SpecificationBuilder<T> {

    Specification<T> buildByCriteria(SearchCriteria criteria);

    default void addDateFilterPredicate(List<Predicate> predicates,
                                        CriteriaBuilder criteriaBuilder,
                                        Root root,
                                        SearchCriteria criteria) {
        if (Objects.nonNull(criteria.getCreateFrom())) {
            predicates.add(
                    criteriaBuilder.greaterThan(root.get(BaseEntity.Fields.createAt), criteria.getCreateFrom())
            );
        }
        if (Objects.nonNull(criteria.getCreateTo())) {
            predicates.add(
                    criteriaBuilder.lessThan(root.get(BaseEntity.Fields.createAt), criteria.getCreateTo())
            );
        }
    }
}
