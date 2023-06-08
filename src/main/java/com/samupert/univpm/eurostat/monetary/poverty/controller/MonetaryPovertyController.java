package com.samupert.univpm.eurostat.monetary.poverty.controller;

import com.samupert.univpm.eurostat.filtering.SearchCriteriaDto;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.SearchCriteriaMapper;
import com.samupert.univpm.eurostat.filtering.criteria.mapper.CriteriaMapperFactory;
import com.samupert.univpm.eurostat.filtering.criteria.SearchCriteriaSpecification;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPovertyService;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPovertySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monetary-poverty")
@AllArgsConstructor
public class MonetaryPovertyController {

    private final MonetaryPovertyService monetaryPovertyService;
    private final CriteriaMapperFactory criteriaMapperFactory;

    @GetMapping
    public List<MonetaryPoverty> getAll(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, Math.min(pageSize, 100));
        return this.monetaryPovertyService.getAll(page).toList();
    }

    @PostMapping("search")
    public List<MonetaryPoverty> searchByCriteria(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestBody SearchCriteriaDto searchCriteria
    ) {
        Pageable page = PageRequest.of(pageNumber, Math.min(pageSize, 100));

        SearchCriteriaMapper criteriaMapper = this.criteriaMapperFactory.getCriteriaMapper(searchCriteria.operation());
        SearchCriteriaSpecification searchCriteriaEntity = criteriaMapper.getEntity(searchCriteria);

//        MonetaryPovertySpecification specification = new MonetaryPovertySpecification(searchCriteriaEntity);
        return this.monetaryPovertyService.searchByCriteria(searchCriteriaEntity, page).toList();
    }
}
