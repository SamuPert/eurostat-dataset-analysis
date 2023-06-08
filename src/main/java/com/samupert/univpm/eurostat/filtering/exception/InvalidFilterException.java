package com.samupert.univpm.eurostat.filtering.exception;

public class InvalidFilterException extends IllegalArgumentException {
    public InvalidFilterException(String errorMessage) {
        super(errorMessage);
    }
}
