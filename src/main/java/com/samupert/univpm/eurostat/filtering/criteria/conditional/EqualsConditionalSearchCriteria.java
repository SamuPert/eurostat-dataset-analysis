package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class EqualsConditionalSearchCriteria extends ConditionalSearchCriteria<Object> {

    public EqualsConditionalSearchCriteria(String fieldName, Object value) {
        this.fieldName = fieldName;

        // Assert that the value is a list of strings
        this.value = value;
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.equal(root.get(fieldName), value);
    }

    @Override
    protected List<Class<?>> getSupportedFieldTypes() {
        return List.of(String.class, Number.class);
    }
}
