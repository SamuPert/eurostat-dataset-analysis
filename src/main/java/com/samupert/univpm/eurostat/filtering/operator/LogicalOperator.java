package com.samupert.univpm.eurostat.filtering.operator;

/**
 * Represents a logical operator.
 */
public enum LogicalOperator implements Operator {

    AND("$and"),
    OR("$or");

    private final String operatorValue;

    LogicalOperator(String operatorValue)
    {
        this.operatorValue = operatorValue;
    }

    @Override
    public String toString() {
        return this.operatorValue;
    }

    @Override
    public OperatorType getOperatorType() {
        return OperatorType.LOGICAL;
    }
}
