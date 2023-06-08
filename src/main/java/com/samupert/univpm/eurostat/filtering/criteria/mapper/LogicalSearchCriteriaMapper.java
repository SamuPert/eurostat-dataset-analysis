package com.samupert.univpm.eurostat.filtering.criteria.mapper;

import com.samupert.univpm.eurostat.filtering.SearchCriteriaDto;
import com.samupert.univpm.eurostat.filtering.criteria.logical.LogicalSearchCriteria;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFilterException;
import com.samupert.univpm.eurostat.filtering.operator.mapper.LogicalOperatorMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LogicalSearchCriteriaMapper implements SearchCriteriaMapper {

    private final LogicalOperatorMapper operatorMapper;
    private final CriteriaMapperFactory criteriaMapperFactory;

    public LogicalSearchCriteriaMapper(LogicalOperatorMapper operatorMapper,
            @Lazy CriteriaMapperFactory criteriaMapperFactory
    ) {
        this.operatorMapper = operatorMapper;
        this.criteriaMapperFactory = criteriaMapperFactory;
    }

    @Override
    public LogicalSearchCriteria getEntity(SearchCriteriaDto dto) throws InvalidFilterException {

        LogicalSearchCriteria logicalSearchCriteria = new LogicalSearchCriteria();

        logicalSearchCriteria.setOperation(this.operatorMapper.getEntity(dto.operation()));

        // For each criteria in the list, map it to the corresponding entity.
        logicalSearchCriteria.setCriteriaList(Arrays.stream(dto.criteriaList()).map(searchCriteriaDto -> {
            SearchCriteriaMapper criteriaMapper = this.criteriaMapperFactory.getCriteriaMapper(searchCriteriaDto.operation());
            try {
                return criteriaMapper.getEntity(searchCriteriaDto);
            }catch (ClassCastException e){
                throw new InvalidFilterException("Unable to process a predicate. Check out the filter field types.");
            }
        }).toList());

        return logicalSearchCriteria;
    }
}
