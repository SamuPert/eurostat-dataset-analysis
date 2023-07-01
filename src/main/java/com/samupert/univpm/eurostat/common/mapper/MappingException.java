package com.samupert.univpm.eurostat.common.mapper;

/**
 * Exception thrown when a mapping fails.
 */
public class MappingException extends RuntimeException {
    /**
     * Creates a new {@link MappingException}.
     *
     * @param message The message to display
     */
    public MappingException(String message) {
        super(message);
    }
}
