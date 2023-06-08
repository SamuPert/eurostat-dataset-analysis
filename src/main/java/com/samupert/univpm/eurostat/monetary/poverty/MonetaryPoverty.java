package com.samupert.univpm.eurostat.monetary.poverty;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
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
