package com.samupert.univpm.eurostat.filtering.criteria.mapper.factory;

import com.samupert.univpm.eurostat.common.mapper.MappingException;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.ConditionalSearchCriteriaMapper;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.LogicalSearchCriteriaMapper;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.SearchCriteriaMapper;
import com.samupert.univpm.eurostat.filtering.operator.Operator;
import com.samupert.univpm.eurostat.filtering.operator.mapper.OperatorMapper;
import jakarta.annotation.PostConstruct;
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
    private final LogicalSearchCriteriaMapper logicalSearchCriteriaMapper;

    /**
     * The {@link ConditionalSearchCriteriaMapper} used to map the search criteria to the {@link Specification}.
     */
    private final ConditionalSearchCriteriaMapper conditionalSearchCriteriaMapper;

    @PostConstruct
    public void initCriteriaMapperFactory() {
        this.logicalSearchCriteriaMapper.setCriteriaMapperFactory(this);
    }

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

        return switch (operator.getOperatorType()) {
            case LOGICAL -> this.logicalSearchCriteriaMapper;
            case CONDITIONAL -> this.conditionalSearchCriteriaMapper;
            default -> throw new MappingException("Invalid operator type: " + operator.getOperatorType());
        };
    }
}
