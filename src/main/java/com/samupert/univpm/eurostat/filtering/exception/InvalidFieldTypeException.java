package com.samupert.univpm.eurostat.filtering.exception;

public class InvalidFieldTypeException extends InvalidFilterException {
    public InvalidFieldTypeException(Class<?> classType) {
        super("Invalid field type: %s".formatted(classType));
    }
}
