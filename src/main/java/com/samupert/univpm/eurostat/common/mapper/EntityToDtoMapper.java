package com.samupert.univpm.eurostat.common.mapper;

public interface EntityToDtoMapper<D, E> {
    D getDto(E entity);
}
