package com.samupert.univpm.eurostat.filtering.operator.mapper;

import com.samupert.univpm.eurostat.common.mapper.DtoToEntityMapper;
import com.samupert.univpm.eurostat.common.mapper.EntityToDtoMapper;
import com.samupert.univpm.eurostat.filtering.exception.InvalidOperatorException;
import com.samupert.univpm.eurostat.filtering.operator.LogicalOperator;
import org.springframework.stereotype.Component;

@Component
public class LogicalOperatorMapper implements DtoToEntityMapper<String, LogicalOperator>, EntityToDtoMapper<String, LogicalOperator> {
    @Override
    public String getDto(LogicalOperator entity) {
        return entity.toString();
    }

    @Override
    public LogicalOperator getEntity(String operatorValue) throws InvalidOperatorException {
        // Map from String to LogicalOperator.
        for (LogicalOperator operator : LogicalOperator.values()) {
            if (operator.toString().equals(operatorValue)) {
                return operator;
            }
        }

        throw new InvalidOperatorException(operatorValue);
    }
}
