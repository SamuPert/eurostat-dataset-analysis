package com.samupert.univpm.eurostat.dataset;

import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPoverty;
import com.samupert.univpm.eurostat.monetary.poverty.MonetaryPovertyFieldSetMapper;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * CSV dataset configuration.
 */
@Configuration
@Slf4j
@DependsOn(value = "CsvDatasetResource")
public class CsvDatasetConfiguration {

    /**
     * Job to import the CSV dataset.
     *
     * @param jobRepository The repository where the jobs are stored.
     * @param csvDatasetImportStep The step to import the CSV dataset.
     * @return The job to import the CSV dataset.
     */
    @Bean
    public Job csvDatasetImportJob(JobRepository jobRepository,
            Step csvDatasetImportStep
    ) {
        return new JobBuilder("CsvDatasetImportJob", jobRepository)
                       .start(csvDatasetImportStep) //
                       .build();
    }

    /**
     * Step to import the CSV dataset.
     *
     * @param jobRepository The repository where the jobs are stored.
     * @param transactionManager The transaction manager used to execute the steps.
     * @param csvItemReader The reader to read the CSV dataset.
     * @param csvItemWriter The writer to write the CSV dataset to.
     * @return The step to import the CSV dataset.
     */
    @Bean
    public Step csvDatasetImportStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<MonetaryPoverty> csvItemReader,
            ItemWriter<MonetaryPoverty> csvItemWriter
    ) {
        return new StepBuilder("CsvDatasetImportStep", jobRepository)
                       .<MonetaryPoverty, MonetaryPoverty>chunk(1000, transactionManager)
                       .reader(csvItemReader)
                       .writer(csvItemWriter)
                       .build();
    }

    /**
     * Reader to read the CSV dataset.
     *
     * @param csvResource The CSV dataset resource to import.
     * @return The reader to read the CSV dataset.
     */
    @Bean
    public ItemReader<MonetaryPoverty> csvItemReader(Resource csvResource) {
        FlatFileItemReader<MonetaryPoverty> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(1);
        itemReader.setResource(csvResource);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    /**
     * Writer to write the CSV dataset to.
     *
     * @param entityManagerFactory The entity manager factory to use to write the CSV dataset to.
     * @return The writer to write the CSV dataset to.
     *
     * @throws Exception When the writer cannot be created.
     */
    @Bean
    public ItemWriter<MonetaryPoverty> csvItemWriter(EntityManagerFactory entityManagerFactory) throws Exception {
        JpaItemWriter<MonetaryPoverty> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    /**
     * Line mapper to map the CSV dataset rows to the {@link MonetaryPoverty} class.
     *
     * @return The line mapper to map the CSV dataset.
     */
    @Bean
    public LineMapper<MonetaryPoverty> lineMapper() {
        DefaultLineMapper<MonetaryPoverty> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(fieldSetMapper());
        lineMapper.setLineTokenizer(lineTokenizer());
        return lineMapper;
    }

    /**
     * Line tokenizer to tokenize the CSV dataset rows.
     *
     * @return The line tokenizer to tokenize the CSV dataset.
     */
    @Bean
    public LineTokenizer lineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("DATAFLOW", "LAST UPDATE", "freq", "wstatus", "indic_il", "sex", "age", "unit", "geo", "TIME_PERIOD", "OBS_VALUE", "OBS_FLAG");
        return lineTokenizer;
    }

    /**
     * Field set mapper to map the CSV dataset rows to the {@link MonetaryPoverty} class.
     *
     * @return The field set mapper to map the CSV dataset rows to the {@link MonetaryPoverty} class.
     */
    @Bean
    public FieldSetMapper<MonetaryPoverty> fieldSetMapper() {
        return new MonetaryPovertyFieldSetMapper();
    }
}
