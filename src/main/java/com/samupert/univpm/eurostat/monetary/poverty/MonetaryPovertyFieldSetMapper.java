package com.samupert.univpm.eurostat.monetary.poverty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Field set mapper for Monetary Poverty data.
 * @see MonetaryPoverty
 */
@Slf4j
public class MonetaryPovertyFieldSetMapper implements FieldSetMapper<MonetaryPoverty> {

    private final SimpleDateFormat dateFormatter;

    /**
     * Initialize the field set mapper.
     */
    public MonetaryPovertyFieldSetMapper() {
        this.dateFormatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
    }

    /**
     * It maps the given field set to a Monetary Poverty entity.
     *
     * @param fieldSet the {@link FieldSet} to map.
     * @return the mapped Monetary Poverty entity.
     */
    @Override
    @NonNull
    public MonetaryPoverty mapFieldSet(@NonNull FieldSet fieldSet) {
        MonetaryPoverty monetaryPoverty = new MonetaryPoverty();

        monetaryPoverty.setDataflow(fieldSet.readString("DATAFLOW"));

        try {
            String dateString = fieldSet.readString("LAST UPDATE");
            monetaryPoverty.setLastUpdate(dateFormatter.parse(dateString));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        monetaryPoverty.setTimeFrequency(fieldSet.readString("freq"));
        monetaryPoverty.setActivityAndEmploymentStatus(fieldSet.readString("wstatus"));
        monetaryPoverty.setIncomeAndLivingConditionsIndicator(fieldSet.readString("indic_il"));
        monetaryPoverty.setSex(fieldSet.readString("sex"));
        monetaryPoverty.setAgeClass(fieldSet.readString("age"));
        monetaryPoverty.setUnitOfMeasure(fieldSet.readString("unit"));
        monetaryPoverty.setGeopoliticalEntity(fieldSet.readString("geo"));
        monetaryPoverty.setTimePeriod(fieldSet.readShort("TIME_PERIOD"));

        try {
            monetaryPoverty.setObservationValue(fieldSet.readDouble("OBS_VALUE"));
        } catch (NumberFormatException e) {
            monetaryPoverty.setObservationValue(null);
        }

        monetaryPoverty.setObservationFlag(fieldSet.readString("OBS_FLAG"));

        return monetaryPoverty;
    }
}
