package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The conditional search criteria specification that checks if the value is not in the list of values. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$nin",
 *     "fieldName": "sex",
 *     "value": [
 *         "M"
 *     ]
 *  }
 * </pre>
 */
public class NotInArrayConditionalSearchCriteria extends InArrayConditionalSearchCriteria {

    /**
     * Creates a new instance of the {@link NotInArrayConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     * @throws ClassCastException Thrown if the value is not a {@link List} of {@link String}.
     */
    public NotInArrayConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
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
