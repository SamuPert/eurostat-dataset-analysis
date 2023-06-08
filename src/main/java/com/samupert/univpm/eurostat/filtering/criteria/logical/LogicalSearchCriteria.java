package com.samupert.univpm.eurostat.filtering.criteria.logical;

import com.samupert.univpm.eurostat.filtering.criteria.SearchCriteriaSpecification;
import com.samupert.univpm.eurostat.filtering.exception.InvalidOperatorException;
import com.samupert.univpm.eurostat.filtering.operator.LogicalOperator;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class LogicalSearchCriteria implements SearchCriteriaSpecification {
    private LogicalOperator operation;
    private List<SearchCriteriaSpecification> criteriaList;

    @Override
    public Predicate toPredicate(@NonNull Root<MonetaryPoverty> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        // Get predicates from child criteria objects recursively.
        Predicate[] childCriteriaPredicates = criteriaList.stream()
                                                          .map(sc -> sc.toPredicate(root, query, criteriaBuilder))
                                                          .toArray(Predicate[]::new);

        return switch (this.operation) {
            case OR -> criteriaBuilder.or(childCriteriaPredicates);
            case AND -> criteriaBuilder.and(childCriteriaPredicates);
            default -> throw new InvalidOperatorException(this.operation.toString());
        };
    }
}
