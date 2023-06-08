package com.samupert.univpm.eurostat.filtering.operator;

public enum ConditionalOperator implements Operator {

    IN_ARRAY("$in"),
    NOT_IN_ARRAY("$nin"),
    CONTAINS("$cn"),
    DOES_NOT_CONTAIN("$ncn"),
    EQUAL("$eq"),
    NOT_EQUAL("$neq"),
    GREATER_THAN("$gt"),
    GREATER_THAN_EQUAL("$gte"),
    LESS_THAN("$lt"),
    LESS_THAN_EQUAL("$lte");

    private final String operatorValue;

    ConditionalOperator(String operatorValue) {
        this.operatorValue = operatorValue;
    }

    @Override
    public String toString() {
        return this.operatorValue;
    }

    @Override
    public OperatorType getOperatorType() {
        return OperatorType.CONDITIONAL;
    }
}
