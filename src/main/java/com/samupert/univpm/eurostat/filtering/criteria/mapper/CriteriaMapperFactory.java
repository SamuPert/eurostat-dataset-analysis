package com.samupert.univpm.eurostat.filtering.criteria.mapper;

import com.samupert.univpm.eurostat.common.mapper.MappingException;
import com.samupert.univpm.eurostat.filtering.operator.Operator;
import com.samupert.univpm.eurostat.filtering.operator.OperatorType;
import com.samupert.univpm.eurostat.filtering.operator.mapper.OperatorMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Factory for creating {@link SearchCriteriaMapper} based on the operator type.
 */
@Component
@AllArgsConstructor
public class CriteriaMapperFactory {

    /**
     * The {@link OperatorMapper} used to map the operator type to the {@link Operator}.
     */
    private final OperatorMapper operatorMapper;

    /**
     * The {@link LogicalSearchCriteriaMapper} used to map the search criteria to the {@link Specification}.
     */
    private final @Lazy LogicalSearchCriteriaMapper logicalSearchCriteriaMapper;

    /**
     * The {@link ConditionalSearchCriteriaMapper} used to map the search criteria to the {@link Specification}.
     */
    private final @Lazy ConditionalSearchCriteriaMapper conditionalSearchCriteriaMapper;

    /**
     * Get the {@link SearchCriteriaMapper} based on the operator type.
     *
     * @param operatorType The operator type to map to the {@link SearchCriteriaMapper}.
     * @return The mapped {@link SearchCriteriaMapper} based on the operator type.
     *
     * @throws MappingException Thrown if the operator type is invalid.
     */
    public SearchCriteriaMapper getCriteriaMapper(String operatorType) throws MappingException {
        Operator operator = operatorMapper.getEntity(operatorType);

        if (operator.getOperatorType() == OperatorType.LOGICAL) {
            return this.logicalSearchCriteriaMapper;
        }

        if (operator.getOperatorType() == OperatorType.CONDITIONAL) {
            return this.conditionalSearchCriteriaMapper;
        }

        throw new MappingException("Invalid operator type");
    }
}
