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
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Abstract class that represents a conditional search criteria that can be applied to a {@link MonetaryPoverty} entity.
 *
 * @param <V> The type of the value of the search criteria.
 */
@Data
@Slf4j
public abstract class ConditionalSearchCriteria<V> implements SearchCriteriaSpecification {

    /**
     * Creates a conditional search criteria that filters a value of type {@link V}.
     */
    public ConditionalSearchCriteria() {
    }

    /**
     * The field name of the search criteria.
     */
    protected String fieldName;

    /**
     * The value of the search criteria.
     */
    protected V value;

    /**
     * The operation to filter the search criteria with.
     */
    private ConditionalOperator operation;

    /**
     * Validates the value of the search criteria.
     *
     * @throws InvalidFilterException Thrown if the value of the search criteria is not supported by the search criteria.
     */
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

    /**
     * Checks if the field name of the search criteria is valid.
     *
     * @param root A root type in the from clause. Query roots always reference entities.
     * @throws InvalidFieldNameException Thrown if the field name of the search criteria is not a valid field name.
     */
    protected void validateFieldName(Root<MonetaryPoverty> root) throws InvalidFieldNameException {
        if (root.get(fieldName) == null) {
            throw new InvalidFieldNameException(fieldName);
        }
    }

    @Override
    public Predicate toPredicate(@NonNull Root<MonetaryPoverty> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder
    ) throws InvalidFilterException {
        this.validateFieldName(root);
        this.validateArgs();
        try {
            return this.getPredicate(root, query, criteriaBuilder);
        } catch (HibernateException | IllegalArgumentException | ClassCastException e) {
            throw new InvalidFilterException("Unable to process a predicate. Check out the filter syntax and field types.");
        }
    }

    /**
     * Wrapper method that returns the predicate of the search criteria.
     *
     * @param root          A root type in the from clause. Query roots always reference entities.
     * @param query         The query to be built.
     * @param criteriaBuilder The criteria builder to be used.
     * @return The predicate of the search criteria.
     *
     * @throws InvalidFilterException Thrown if the search criteria is not a valid filter.
     */
    protected abstract Predicate getPredicate(Root<MonetaryPoverty> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) throws InvalidFilterException;

    /**
     * Gets the list of supported field types of the search criteria.
     *
     * @return The list of supported field types of the search criteria.
     */
    protected abstract List<Class<?>> getSupportedFieldTypes();

}
