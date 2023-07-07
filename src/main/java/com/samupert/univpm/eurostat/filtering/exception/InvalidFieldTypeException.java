package com.samupert.univpm.eurostat.filtering.exception;

/**
 * Exception thrown when the field type is invalid.
 */
public class InvalidFieldTypeException extends InvalidFilterException {
    /**
     * Create a new {@link InvalidFieldTypeException}.
     *
     * @param classType The field type that is invalid.
     */
    public InvalidFieldTypeException(Class<?> classType) {
        super("Invalid field type: %s".formatted(classType));
    }
}
