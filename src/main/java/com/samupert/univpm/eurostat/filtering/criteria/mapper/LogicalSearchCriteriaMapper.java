package com.samupert.univpm.eurostat.filtering.criteria.mapper;

import com.samupert.univpm.eurostat.filtering.SearchCriteriaDto;
import com.samupert.univpm.eurostat.filtering.criteria.SearchCriteriaSpecification;
import com.samupert.univpm.eurostat.filtering.criteria.logical.LogicalSearchCriteria;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.factory.CriteriaMapperFactory;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFilterException;
import com.samupert.univpm.eurostat.filtering.operator.mapper.LogicalOperatorMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Mapper for the logical search criteria.
 */
@Component
public class LogicalSearchCriteriaMapper implements SearchCriteriaMapper {

    /**
     * Mapper for the logical operator.
     */
    private final LogicalOperatorMapper operatorMapper;

    /**
     * Mapper factory to create the mapper for the criteria.
     */
    private CriteriaMapperFactory criteriaMapperFactory;

    /**
     * Create a new mapper for the logical search criteria.
     *
     * @param operatorMapper The mapper for the logical operator.
     */
    public LogicalSearchCriteriaMapper(LogicalOperatorMapper operatorMapper
    ) {
        this.operatorMapper = operatorMapper;
    }

    @Override
    public LogicalSearchCriteria getEntity(SearchCriteriaDto dto) throws InvalidFilterException {

        LogicalSearchCriteria logicalSearchCriteria = new LogicalSearchCriteria();

        logicalSearchCriteria.setOperation(this.operatorMapper.getEntity(dto.operation()));

        Stream<SearchCriteriaDto> criteriaListStream = Arrays.stream(dto.criteriaList());

        Stream<SearchCriteriaSpecification> searchCriteriaSpecificationStream = criteriaListStream.map(searchCriteriaDto -> {
            SearchCriteriaMapper criteriaMapper = this.criteriaMapperFactory.getCriteriaMapper(searchCriteriaDto.operation());
            try {
                return criteriaMapper.getEntity(searchCriteriaDto);
            }catch (ClassCastException e){
                throw new InvalidFilterException("Unable to process a predicate. Check out the filter field types.");
            }
        });

        // For each criterion in the list, map it to the corresponding entity.
        logicalSearchCriteria.setCriteriaList(searchCriteriaSpecificationStream.toList());

        return logicalSearchCriteria;
    }

    public void setCriteriaMapperFactory(CriteriaMapperFactory criteriaMapperFactory) {
        this.criteriaMapperFactory = criteriaMapperFactory;
    }
}
