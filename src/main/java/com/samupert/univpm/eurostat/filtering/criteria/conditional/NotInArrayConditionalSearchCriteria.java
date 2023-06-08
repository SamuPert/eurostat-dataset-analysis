package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NotInArrayConditionalSearchCriteria extends InArrayConditionalSearchCriteria {

    public NotInArrayConditionalSearchCriteria(String fieldName, Object value) {
        super(fieldName, value);
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.not(super.getPredicate(root, query, criteriaBuilder));
    }
}
