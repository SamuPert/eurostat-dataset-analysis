package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The conditional search criteria specification that checks if the field value is greater than the given value. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$gt",
 *     "fieldName": "timePeriod",
 *     "value": 2018
 *  }
 * </pre>
 */
public class GreaterThanConditionalSearchCriteria extends ConditionalSearchCriteria<Number> {

    /**
     * Creates a new instance of the {@link GreaterThanConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     * @throws ClassCastException Thrown if the value is not a {@link Number}.
     */
    public GreaterThanConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
        this.fieldName = fieldName;
        this.value = (Number) value;
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.greaterThan(root.get(fieldName), Double.parseDouble(String.valueOf(value)));
    }

    @Override
    protected List<Class<?>> getSupportedFieldTypes() {
        return List.of(Number.class);
    }
}
