package com.samupert.univpm.eurostat.filtering.operator;

/**
 * Represents a logical operator.
 */
public enum LogicalOperator implements Operator {

    /**
     * Logical operator enum for the $and operator.
     */
    AND("$and"),

    /**
     * Logical operator enum for the $or operator.
     */
    OR("$or");

    /**
     * String representation of the operator.
     */
    private final String operatorValue;

    /**
     * Stores the string representation of the operator.
     *
     * @param operatorValue The string representation of the operator.
     */
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
