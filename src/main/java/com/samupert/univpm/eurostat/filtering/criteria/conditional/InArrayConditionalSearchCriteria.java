package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The conditional search criteria specification that checks if the value is in the list of values. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$in",
 *     "fieldName": "sex",
 *     "value": [
 *         "M"
 *     ]
 *  }
 * </pre>
 */
public class InArrayConditionalSearchCriteria extends ConditionalSearchCriteria<List<String>> {

    /**
     * Creates a new instance of the {@link InArrayConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     * @throws ClassCastException Thrown if the value is not a {@link List} of {@link String}.
     */
    public InArrayConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
        this.fieldName = fieldName;
        this.value = (List<String>) value;
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.in(root.get(fieldName)).value(value);
    }

    @Override
    protected List<Class<?>> getSupportedFieldTypes() {
        return List.of(List.class);
    }
}
