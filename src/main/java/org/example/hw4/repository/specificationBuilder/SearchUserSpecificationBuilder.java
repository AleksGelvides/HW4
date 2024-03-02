package org.example.hw4.repository.specificationBuilder;

import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.example.hw4.repository.data.User;
import org.example.hw4.web.dto.request.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class SearchUserSpecificationBuilder implements SpecificationBuilder<User>{

    @Override
    public Specification<User> buildByCriteria(SearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {

            if (Objects.isNull(criteria)) return null;

            List<Predicate> predicates = new LinkedList<>();

            if (StringUtils.isNotEmpty(criteria.getSearchWord())) {
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(User.Fields.firstName)),
                                        MessageFormat.format("%{0}%", criteria.getSearchWord()).toLowerCase()),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(User.Fields.lastName)),
                                        MessageFormat.format("%{0}%", criteria.getSearchWord()).toLowerCase()),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(User.Fields.login)),
                                        MessageFormat.format("%{0}%", criteria.getSearchWord()).toLowerCase())
                        )
                );
            }

            addDateFilterPredicate(predicates, criteriaBuilder, root, criteria);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
