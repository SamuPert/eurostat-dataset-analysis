package com.samupert.univpm.eurostat.common.mapper;

public interface DtoMapper<D, E> {
    D getDto(E entity);
    E getEntity(D dto);
}
