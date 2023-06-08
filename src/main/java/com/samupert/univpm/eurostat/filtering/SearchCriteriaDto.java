package com.samupert.univpm.eurostat.filtering;


import com.samupert.univpm.eurostat.filtering.criteria.LogicalSearchCriteria;
import com.samupert.univpm.eurostat.filtering.operator.Operator;
import org.springframework.lang.NonNull;

/**
 * Class that represents a search criteria.
 *
 * @param operation The operation to be performed. {@link Operator}
 * @param criteriaList The list of criteria to be applied. {@link LogicalSearchCriteria}
 * @param fieldName The field name to be filtered.
 * @param value The value to be filtered.
 */
public record SearchCriteriaDto(
        @NonNull
        String operation,
        SearchCriteriaDto[] criteriaList,
        String fieldName,
        Object value
) {
}
