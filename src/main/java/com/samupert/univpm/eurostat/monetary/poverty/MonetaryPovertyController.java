package com.samupert.univpm.eurostat.monetary.poverty;

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

    @GetMapping
    public List<MonetaryPoverty> getAll(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, Math.min(pageSize, 100));
        return this.monetaryPovertyService.getAll(page).toList();
    }
}
