package com.samupert.univpm.eurostat.filtering.criteria.mapper;

import com.samupert.univpm.eurostat.common.mapper.DtoToEntityMapper;
import com.samupert.univpm.eurostat.filtering.SearchCriteriaDto;
import com.samupert.univpm.eurostat.filtering.criteria.SearchCriteriaSpecification;

/**
 * Alias for Dto to entity mapper for search criteria.
 */
public interface SearchCriteriaMapper extends DtoToEntityMapper<SearchCriteriaDto, SearchCriteriaSpecification> {
}
