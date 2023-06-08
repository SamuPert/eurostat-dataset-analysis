package com.samupert.univpm.eurostat.filtering.criteria.mapper;

import com.samupert.univpm.eurostat.common.mapper.MappingException;
import com.samupert.univpm.eurostat.filtering.operator.Operator;
import com.samupert.univpm.eurostat.filtering.operator.OperatorType;
import com.samupert.univpm.eurostat.filtering.operator.mapper.OperatorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CriteriaMapperFactory {
    private final OperatorMapper operatorMapper;
    private final LogicalSearchCriteriaMapper logicalSearchCriteriaMapper;
    private final ConditionalSearchCriteriaMapper conditionalSearchCriteriaMapper;

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
