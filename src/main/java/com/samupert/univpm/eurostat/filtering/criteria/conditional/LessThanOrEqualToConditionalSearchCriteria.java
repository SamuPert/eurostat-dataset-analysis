package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class LessThanOrEqualToConditionalSearchCriteria extends ConditionalSearchCriteria<Number> {
    public LessThanOrEqualToConditionalSearchCriteria(String fieldName, Object value) {
        this.fieldName = fieldName;

        // Assert that the value is a list of strings
        this.value = ((Number) value);
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), Double.parseDouble(String.valueOf(value)));
    }

    @Override
    protected List<Class<?>> getSupportedFieldTypes() {
        return List.of(Number.class);
    }
}
