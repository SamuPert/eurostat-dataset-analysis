package com.samupert.univpm.eurostat.filtering.exception;

/**
 * Thrown when the operator is not valid.
 */
public class InvalidOperatorException extends InvalidFilterException {
    /**
     * Create a new {@link InvalidOperatorException}.
     *
     * @param operatorValue The operator value that is not valid.
     */
    public InvalidOperatorException(String operatorValue) {
        super("Invalid operator: %s".formatted(operatorValue));
    }
}
