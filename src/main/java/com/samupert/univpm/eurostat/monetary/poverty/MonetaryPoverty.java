package com.samupert.univpm.eurostat.monetary.poverty;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.Date;

/**
 * The MonetaryPoverty entity, that represents the Monetary Poverty data of the dataset.
 */
@Entity
@Data
public class MonetaryPoverty {

    /**
     * The unique identifier of the Monetary Poverty data.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The dataflow of the Monetary Poverty data.
     */
    private String dataflow;

    /**
     * The last update timestamp of the Monetary Poverty data.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date lastUpdate;

    /**
     * Represents of frequent is the data updated.
     */
    private String timeFrequency;

    /**
     * Represents the activity and employment status.
     */
    private String activityAndEmploymentStatus;

    /**
     * Represents the income and living condition indicator.
     */
    private String incomeAndLivingConditionsIndicator;

    /**
     * Represents the sex.
     */
    private String sex;

    /**
     * Represents the age class.
     */
    private String ageClass;

    /**
     * Represents the unit of measure of the data.
     */
    private String unitOfMeasure;

    /**
     * Represents the geopolitical entity the data is referred to.
     */
    private String geopoliticalEntity;

    /**
     * Represents the time period the data is referred to.
     */
    private Short timePeriod;

    /**
     * Represents the value of the observation.
     */
    private Double observationValue;

    /**
     * Represents the flag of the observation. (e: estimated, p: provisional, b: break in time series, ...)
     */
    private String observationFlag;
}
