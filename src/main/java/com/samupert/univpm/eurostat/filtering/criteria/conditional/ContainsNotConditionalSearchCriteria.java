package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * The conditional search criteria specification that checks if the field value doesn't contain the given value. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$ncn",
 *     "fieldName": "ageClass",
 *     "value": "16-64"
 *  }
 * </pre>
 */
public class ContainsNotConditionalSearchCriteria extends ContainsConditionalSearchCriteria {

    /**
     * Creates a new instance of the {@link ContainsNotConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     * @throws ClassCastException Thrown if the value is not a {@link String}.
     */
    public ContainsNotConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
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
