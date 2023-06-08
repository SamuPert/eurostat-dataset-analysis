package com.samupert.univpm.eurostat.filtering.operator.mapper;

import com.samupert.univpm.eurostat.common.mapper.DtoToEntityMapper;
import com.samupert.univpm.eurostat.common.mapper.EntityToDtoMapper;
import com.samupert.univpm.eurostat.filtering.exception.InvalidOperatorException;
import com.samupert.univpm.eurostat.filtering.operator.ConditionalOperator;
import org.springframework.stereotype.Component;

@Component
public class ConditionalOperatorMapper implements DtoToEntityMapper<String, ConditionalOperator>, EntityToDtoMapper<String, ConditionalOperator> {
    @Override
    public String getDto(ConditionalOperator entity) {
        return entity.toString();
    }

    @Override
    public ConditionalOperator getEntity(String operatorValue) throws InvalidOperatorException {
        // Map from String to ConditionalOperator.
        for (ConditionalOperator operator : ConditionalOperator.values()) {
            if (operator.toString().equals(operatorValue)) {
                return operator;
            }
        }

        throw new InvalidOperatorException(operatorValue);
    }
}
