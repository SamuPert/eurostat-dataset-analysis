package com.samupert.univpm.eurostat.filtering.criteria;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import org.springframework.data.jpa.domain.Specification;

/**
 * Alias to Specification of MonetaryPoverty.
 */
public interface SearchCriteriaSpecification extends Specification<MonetaryPoverty> {
}
