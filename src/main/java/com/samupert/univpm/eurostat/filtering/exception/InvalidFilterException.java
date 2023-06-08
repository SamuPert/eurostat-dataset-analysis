package com.samupert.univpm.eurostat.filtering.exception;

public class InvalidFilterException extends IllegalArgumentException {
    public InvalidFilterException() {
        super("Invalid filter. Check documentation for valid filters or check syntax.");
    }

    public InvalidFilterException(String errorMessage) {
        super(errorMessage);
    }
}
