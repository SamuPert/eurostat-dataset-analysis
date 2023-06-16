package com.samupert.univpm.eurostat.common.metadata;

/**
 * Metadata of a field of an entity.
 *
 * @param name The name of the field.
 * @param type The type of the field.
 */
public record EntityFieldMetadata(String name, String type) {
}
