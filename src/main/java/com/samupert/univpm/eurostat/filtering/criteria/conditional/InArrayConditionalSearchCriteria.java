package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.common.mapper.MappingException;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.Assert;

import java.util.List;

public class InArrayConditionalSearchCriteria extends ConditionalSearchCriteria<List<String>> {

    public InArrayConditionalSearchCriteria(String fieldName, Object value) {
        this.fieldName = fieldName;

        // Assert that the value is a list of strings
        try{
            Assert.isInstanceOf(List.class, value);
            ((List<?>) value).forEach(v -> Assert.isInstanceOf(String.class, v));
        }catch (IllegalArgumentException e){
            throw new MappingException("The value must be a list of strings");
        }

        this.value = (List<String>) value;
    }

    @Override
    protected Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.in(root.get(fieldName)).value(value);
    }

    @Override
    protected List<Class<?>> getSupportedFieldTypes() {
        return List.of(List.class);
    }
}
