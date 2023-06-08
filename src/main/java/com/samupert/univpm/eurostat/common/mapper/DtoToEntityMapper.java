package com.samupert.univpm.eurostat.common.mapper;

public interface DtoToEntityMapper<D, E> {
    E getEntity(D dto) throws MappingException;
}
