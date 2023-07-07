package com.samupert.univpm.eurostat.monetary.poverty;

import com.samupert.univpm.eurostat.common.metadata.EntityFieldMetadata;
import com.samupert.univpm.eurostat.common.metadata.EntityMetadata;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that handles the business logic of the Monetary Poverty entity.
 */
@Service
@AllArgsConstructor
public class MonetaryPovertyService {

    private final MonetaryPovertyRepository repository;

    /**
     * Get all Monetary Poverty entities.
     *
     * @param page The page to retrieve.
     * @return The retrieved Monetary Poverty entities.
     */
    public Page<MonetaryPoverty> getAll(Pageable page) {
        return this.repository.findAll(page);
    }

    /**
     * Get all Monetary Poverty entities that match the given specification.
     *
     * @param specification The filter to apply to the entities.
     * @param page The page to retrieve.
     * @return The filtered Monetary Poverty entities.
     */
    public Page<MonetaryPoverty> searchByCriteria(Specification<MonetaryPoverty> specification, Pageable page) {
        return this.repository.findAll(specification, page);
    }

    /**
     * Get the metadata of the Monetary Poverty entity.
     *
     * @return The metadata of the Monetary Poverty entity.
     * @see EntityMetadata
     */
    public EntityMetadata getMetadata() {
        Class<MonetaryPoverty> monetaryPovertyClass = MonetaryPoverty.class;

        String entityName = monetaryPovertyClass.getName();

        List<EntityFieldMetadata> fieldMetadata = Arrays.stream(monetaryPovertyClass.getDeclaredFields()) //
                                                        // Ignore Hibernate auto-generated fields.
                                                        .filter(field -> !field.getName().startsWith("$")) //
                                                        .map(field -> new EntityFieldMetadata( //
                                                                field.getName(), //
                                                                field.getType().toString()) //
                                                        ) //
                                                        .collect(Collectors.toList());

        return new EntityMetadata(entityName, fieldMetadata);
    }
}
