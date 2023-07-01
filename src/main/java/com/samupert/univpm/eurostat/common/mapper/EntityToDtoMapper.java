package com.samupert.univpm.eurostat.common.mapper;

/**
 * Interface that defines the contract for mapping between entities and DTOs.
 *
 * @param <D> The class of the DTO.
 * @param <E> The class of the entity.
 */
public interface EntityToDtoMapper<D, E> {

    /**
     * Maps an entity to a DTO.
     *
     * @param entity The entity to be mapped.
     * @return The DTO mapped from the entity.
     */
    D getDto(E entity);
}
