package com.samupert.univpm.eurostat.filtering.exception;

/**
 * Exception thrown when the field name is invalid.
 */
public class InvalidFieldNameException extends InvalidFilterException {
    /**
     * Create a new {@link InvalidFieldNameException}.
     *
     * @param fieldName The field name that is invalid.
     */
    public InvalidFieldNameException(String fieldName) {
        super("Invalid field name: %s".formatted(fieldName));
    }
}
