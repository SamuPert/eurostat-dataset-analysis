package com.samupert.univpm.eurostat.filtering.operator;

/**
 * Represents an operator.
 */
public interface Operator {
    /**
     * Gets the operator type. See {@link OperatorType}.
     *
     * @return The operator type.
     */
    OperatorType getOperatorType();
}
