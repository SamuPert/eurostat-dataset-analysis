package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The conditional search criteria specification that checks if the field value contains the given value. See {@link Specification}.
 * <hr />
 * The JSON filter is:
 * <pre>
 * {
 *     "operation": "$cn",
 *     "fieldName": "ageClass",
 *     "value": "16-64"
 *  }
 * </pre>
 */
public class ContainsConditionalSearchCriteria extends ConditionalSearchCriteria<String> {

    /**
     * Creates a new instance of the {@link ContainsConditionalSearchCriteria} class.
     *
     * @param fieldName The field name to filter.
     * @param value The value to filter.
     * @throws ClassCastException Thrown if the value is not a {@link String}.
     */
    public ContainsConditionalSearchCriteria(String fieldName, Object value) throws ClassCastException {
        this.fieldName = fieldName;
        this.value = (String) value;
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.like(root.get(fieldName), "%"+value+"%");
    }

    @Override
    protected List<Class<?>> getSupportedFieldTypes() {
        return List.of(String.class);
    }
}
