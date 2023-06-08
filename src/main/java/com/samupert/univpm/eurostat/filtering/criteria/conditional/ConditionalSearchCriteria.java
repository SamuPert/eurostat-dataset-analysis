package com.samupert.univpm.eurostat.filtering.criteria.conditional;

import com.samupert.univpm.eurostat.filtering.criteria.SearchCriteriaSpecification;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFieldNameException;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFieldTypeException;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFilterException;
import com.samupert.univpm.eurostat.filtering.operator.ConditionalOperator;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;

import java.util.List;

@Data
@Slf4j
public abstract class ConditionalSearchCriteria<V> implements SearchCriteriaSpecification {
    protected String fieldName;
    protected V value;
    private ConditionalOperator operation;

    private void validateArgs() throws InvalidFilterException {
        List<Class<?>> supportedFieldTypes = this.getSupportedFieldTypes();
        Class<?> valueClass = this.value.getClass();

        for (Class<?> supportedFieldType : supportedFieldTypes) {
            if (supportedFieldType.isAssignableFrom(valueClass)) {
                return;
            }
        }

        throw new InvalidFieldTypeException(valueClass);
    }

    protected void validateFieldName(Root<MonetaryPoverty> root) throws InvalidFieldNameException {
        if (root.get(fieldName) == null) {
            throw new InvalidFieldNameException(fieldName);
        }
    }

    @Override
    public Predicate toPredicate(Root<MonetaryPoverty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) throws InvalidFilterException {
        this.validateFieldName(root);
        this.validateArgs();
        try {
            return this.getPredicate(root, query, criteriaBuilder);
        } catch (HibernateException | IllegalArgumentException | ClassCastException e) {
            throw new InvalidFilterException("Unable to process a predicate. Check out the filter syntax and field types.");
        }
    }

    protected abstract Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) throws InvalidFilterException ;

    protected abstract List<Class<?>> getSupportedFieldTypes();

}
