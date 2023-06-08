package com.samupert.univpm.eurostat.monetary.poverty;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MonetaryPovertyService {

    private final MonetaryPovertyRepository repository;

    public Page<MonetaryPoverty> getAll(Pageable page) {
        return this.repository.findAll(page);
    }

    public Page<MonetaryPoverty> searchByCriteria(Specification<MonetaryPoverty> specification, Pageable page) {
        return this.repository.findAll(specification, page);
    }
}
