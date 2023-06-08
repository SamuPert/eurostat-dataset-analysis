package com.samupert.univpm.eurostat.filtering.exception;

public class InvalidOperatorException extends InvalidFilterException {
    public InvalidOperatorException(String operatorValue) {
        super("Invalid operator: %s".formatted(operatorValue));
    }
}
