package com.samupert.univpm.eurostat.common.metadata;

import java.util.List;

public record EntityMetadata(String entity, List<EntityFieldMetadata> fields) {
}
