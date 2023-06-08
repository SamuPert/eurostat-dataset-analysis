package com.samupert.univpm.eurostat.filtering.exception;

public class InvalidFieldNameException extends InvalidFilterException {
    public InvalidFieldNameException(String fieldName) {
        super("Invalid field name: %s".formatted(fieldName));
    }
}
