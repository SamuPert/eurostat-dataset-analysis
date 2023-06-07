package com.samupert.univpm.eurostat.filtering.operator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samupert.univpm.eurostat.filtering.criteria.LogicalOperatorDeserializer;

@JsonDeserialize(using = LogicalOperatorDeserializer.class)
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
}
