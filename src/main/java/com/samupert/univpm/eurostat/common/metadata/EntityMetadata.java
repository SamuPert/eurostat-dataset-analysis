package com.samupert.univpm.eurostat.common.metadata;

import java.util.List;

/**
 * Represents the metadata of an entity.
 *
 * @param entity the entity name.
 * @param fields the list of fields of the entity. See {@link EntityFieldMetadata}.
 */
public record EntityMetadata(String entity, List<EntityFieldMetadata> fields) {
}
