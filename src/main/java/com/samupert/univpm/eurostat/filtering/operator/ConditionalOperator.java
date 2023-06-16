package com.samupert.univpm.eurostat.filtering.operator;

import com.samupert.univpm.eurostat.filtering.criteria.conditional.InArrayConditionalSearchCriteria;

/**
 * Represents the conditional operators that can be used in the filtering process.
 */
public enum ConditionalOperator implements Operator {

    /**
     * Conditional operator enum for the $in operator.
     */
    IN_ARRAY("$in"),

    /**
     * Conditional operator enum for the $nin operator.
     */
    NOT_IN_ARRAY("$nin"),

    /**
     * Conditional operator enum for the $cn operator.
     */
    CONTAINS("$cn"),

    /**
     * Conditional operator enum for the $ncn operator.
     */
    DOES_NOT_CONTAIN("$ncn"),

    /**
     * Conditional operator enum for the $eq operator.
     */
    EQUAL("$eq"),

    /**
     * Conditional operator enum for the $neq operator.
     */
    NOT_EQUAL("$neq"),

    /**
     * Conditional operator enum for the $gt operator.
     */
    GREATER_THAN("$gt"),

    /**
     * Conditional operator enum for the $gte operator.
     */
    GREATER_THAN_EQUAL("$gte"),

    /**
     * Conditional operator enum for the $lt operator.
     */
    LESS_THAN("$lt"),

    /**
     * Conditional operator enum for the $lte operator.
     */
    LESS_THAN_EQUAL("$lte");


    /**
     * String representation of the operator.
     */
    private final String operatorValue;

    /**
     * Stores the string representation of the operator.
     *
     * @param operatorValue The string representation of the operator.
     */
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
