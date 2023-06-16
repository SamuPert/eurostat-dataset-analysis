package com.samupert.univpm.eurostat.monetary.poverty.controller;

import com.samupert.univpm.eurostat.common.metadata.EntityMetadata;
import com.samupert.univpm.eurostat.filtering.SearchCriteriaDto;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.SearchCriteriaMapper;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.CriteriaMapperFactory;
import com.samupert.univpm.eurostat.filtering.criteria.SearchCriteriaSpecification;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPovertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the Monetary Poverty entity.
 */
@RestController
@RequestMapping("/api/v1/monetary-poverty")
@AllArgsConstructor
public class MonetaryPovertyController {

    private final MonetaryPovertyService monetaryPovertyService;
    private final CriteriaMapperFactory criteriaMapperFactory;

    /**
     * Get all Monetary Poverty data.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize The number of elements per page.
     * @return The list of Monetary Poverty data for the given page.
     */
    @GetMapping
    public List<MonetaryPoverty> getAll(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, Math.min(pageSize, 100));
        return this.monetaryPovertyService.getAll(page).toList();
    }

    /**
     * Get all Monetary Poverty data filtered by a given search criteria.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize The number of elements per page.
     * @param searchCriteria The search criteria to apply. See {@link SearchCriteriaDto}.
     * @return The list of Monetary Poverty data for the given page, filtered by the provided search criteria.
     */
    @PostMapping("search")
    public List<MonetaryPoverty> searchByCriteria(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestBody SearchCriteriaDto searchCriteria
    ) {
        Pageable page = PageRequest.of(pageNumber, Math.min(pageSize, 100));

        SearchCriteriaMapper criteriaMapper = this.criteriaMapperFactory.getCriteriaMapper(searchCriteria.operation());
        SearchCriteriaSpecification searchCriteriaEntity = criteriaMapper.getEntity(searchCriteria);

        return this.monetaryPovertyService.searchByCriteria(searchCriteriaEntity, page).toList();
    }

    /**
     * Gets the metadata of the Monetary Poverty entity.
     *
     * @return The metadata of the Monetary Poverty entity. See {@link EntityMetadata}.
     */
    @GetMapping("metadata")
    public ResponseEntity<EntityMetadata> getMetadata() {
        return ResponseEntity.ok(this.monetaryPovertyService.getMetadata());
    }
}
