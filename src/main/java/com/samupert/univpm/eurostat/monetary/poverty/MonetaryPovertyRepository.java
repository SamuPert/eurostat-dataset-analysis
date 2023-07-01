package com.samupert.univpm.eurostat.monetary.poverty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface MonetaryPovertyRepository extends JpaRepository<MonetaryPoverty, Long>, JpaSpecificationExecutor<MonetaryPoverty> {
}
