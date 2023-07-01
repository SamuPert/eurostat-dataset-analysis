package com.samupert.univpm.eurostat.filtering.operator.mapper;

import com.samupert.univpm.eurostat.common.mapper.DtoToEntityMapper;
import com.samupert.univpm.eurostat.common.mapper.EntityToDtoMapper;
import com.samupert.univpm.eurostat.filtering.exception.InvalidOperatorException;
import com.samupert.univpm.eurostat.filtering.operator.Operator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The generic mapper for the Operator class.
 * It's used to convert a {@link String} to a {@link Operator} and viceversa.
 *
 * @see Operator
 */
@Component
@AllArgsConstructor
public class OperatorMapper implements DtoToEntityMapper<String, Operator>, EntityToDtoMapper<String, Operator> {

    private final LogicalOperatorMapper logicalOperatorMapper;
    private final ConditionalOperatorMapper conditionalOperatorMapper;

    @Override
    public String getDto(Operator entity) {
        return entity.toString();
    }

    @Override
    public Operator getEntity(String operatorValue) throws InvalidOperatorException {

        try{
            return this.logicalOperatorMapper.getEntity(operatorValue);
        }catch (InvalidOperatorException e){
            // LogicalOperatorMapper threw an exception, so it's not a LogicalOperator.
        }

        try{
            return this.conditionalOperatorMapper.getEntity(operatorValue);
        }catch (InvalidOperatorException e){
            // ConditionalOperatorMapper threw an exception, so it's not a ConditionalOperator.
        }

        throw new InvalidOperatorException(operatorValue);
    }
}
