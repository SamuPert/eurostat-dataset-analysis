package com.samupert.univpm.eurostat.filtering.exception;

/**
 * Exception thrown when the filter is not valid.
 */
public class InvalidFilterException extends IllegalArgumentException {
    /**
     * Create a new {@link InvalidFilterException}.
     *
     * @param errorMessage The error message.
     */
    public InvalidFilterException(String errorMessage) {
        super(errorMessage);
    }
}
