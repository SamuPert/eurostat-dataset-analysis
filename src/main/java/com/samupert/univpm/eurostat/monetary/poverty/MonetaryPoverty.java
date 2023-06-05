package com.samupert.univpm.eurostat.monetary.poverty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class MonetaryPoverty {

    @Id
    @GeneratedValue
    private Long id;

    private String dataflow;
    private Date lastUpdate;
    private String timeFrequency;
    private String activityAndEmploymentStatus;
    private String incomeAndLivingConditionsIndicator;
    private String sex;
    private String ageClass;
    private String unitOfMeasure;
    private String geopoliticalEntity;
    private Short timePeriod;
    private Double observationValue;
    private String observationFlag;
}
