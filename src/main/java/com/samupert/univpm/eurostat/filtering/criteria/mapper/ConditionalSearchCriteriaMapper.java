package com.samupert.univpm.eurostat.filtering.criteria.mapper;

import com.samupert.univpm.eurostat.filtering.SearchCriteriaDto;
import com.samupert.univpm.eurostat.filtering.criteria.conditional.ConditionalSearchCriteria;
import com.samupert.univpm.eurostat.filtering.criteria.conditional.*;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFieldNameException;
import com.samupert.univpm.eurostat.filtering.exception.InvalidOperatorException;
import com.samupert.univpm.eurostat.filtering.operator.ConditionalOperator;
import com.samupert.univpm.eurostat.filtering.operator.mapper.ConditionalOperatorMapper;
import org.springframework.stereotype.Component;

/**
 * This class maps a {@link SearchCriteriaDto} to a {@link ConditionalSearchCriteria}.
 * @see SearchCriteriaMapper
 */
@Component
public class ConditionalSearchCriteriaMapper implements SearchCriteriaMapper {

    /**
     * The {@link ConditionalOperatorMapper} used to map a {@link SearchCriteriaDto} to a {@link ConditionalOperator}.
     */
    private final ConditionalOperatorMapper operatorMapper;

    /**
     * Creates a new {@link ConditionalSearchCriteriaMapper} with the given {@link ConditionalOperatorMapper}.
     *
     * @param operatorMapper The {@link ConditionalOperatorMapper} used to map a {@link SearchCriteriaDto} to a {@link ConditionalOperator}.
     */
    public ConditionalSearchCriteriaMapper(ConditionalOperatorMapper operatorMapper) {
        this.operatorMapper = operatorMapper;
    }

    @Override
    public ConditionalSearchCriteria<?> getEntity(SearchCriteriaDto dto) throws InvalidOperatorException, InvalidFieldNameException {

        ConditionalOperator operator = this.operatorMapper.getEntity(dto.operation());

        return switch (operator) {
            case IN_ARRAY -> new InArrayConditionalSearchCriteria(dto.fieldName(), dto.value());
            case NOT_IN_ARRAY -> new NotInArrayConditionalSearchCriteria(dto.fieldName(), dto.value());
            case CONTAINS -> new ContainsConditionalSearchCriteria(dto.fieldName(), dto.value());
            case DOES_NOT_CONTAIN -> new ContainsNotConditionalSearchCriteria(dto.fieldName(), dto.value());
            case EQUAL -> new EqualsConditionalSearchCriteria(dto.fieldName(), dto.value());
            case NOT_EQUAL -> new EqualsNotConditionalSearchCriteria(dto.fieldName(), dto.value());
            case GREATER_THAN -> new GreaterThanConditionalSearchCriteria(dto.fieldName(), dto.value());
            case GREATER_THAN_EQUAL -> new GreaterThanOrEqualToConditionalSearchCriteria(dto.fieldName(), dto.value());
            case LESS_THAN -> new LessThanConditionalSearchCriteria(dto.fieldName(), dto.value());
            case LESS_THAN_EQUAL -> new LessThanOrEqualToConditionalSearchCriteria(dto.fieldName(), dto.value());
            default -> throw new InvalidOperatorException(dto.operation());
        };
    }
}
