package com.samupert.univpm.eurostat.common.mapper;

/**
 * Interface that defines the contract for mapping between DTOs and entities.
 *
 * @param <D> The class of the DTO.
 * @param <E> The class of the entity.
 */
public interface DtoToEntityMapper<D, E> {

    /**
     * Maps a DTO to an entity.
     *
     * @param dto The DTO to map.
     * @return The mapped entity.
     * @throws MappingException If the mapping fails.
     */
    E getEntity(D dto) throws MappingException;
}
