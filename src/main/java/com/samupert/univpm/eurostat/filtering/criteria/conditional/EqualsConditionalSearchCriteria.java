package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The conditional search criteria specification that checks if the field value is equal to the given value. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$eq",
 *     "fieldName": "activityAndEmploymentStatus",
 *     "value": "EMP"
 *  }
 * </pre>
 */
public class EqualsConditionalSearchCriteria extends ConditionalSearchCriteria<Object> {

    /**
     * Creates a new instance of the {@link EqualsConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     */
    public EqualsConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
        this.fieldName = fieldName;
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
