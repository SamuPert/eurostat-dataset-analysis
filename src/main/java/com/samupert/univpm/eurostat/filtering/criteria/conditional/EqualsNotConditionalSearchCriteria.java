package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * The conditional search criteria specification that checks if the field value isn't equal to the given value. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$neq",
 *     "fieldName": "activityAndEmploymentStatus",
 *     "value": "EMP"
 *  }
 * </pre>
 */
public class EqualsNotConditionalSearchCriteria extends EqualsConditionalSearchCriteria {

    /**
     * Creates a new instance of the {@link EqualsNotConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     */
    public EqualsNotConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
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
