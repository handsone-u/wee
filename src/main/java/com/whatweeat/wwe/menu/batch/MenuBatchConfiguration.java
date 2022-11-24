package com.whatweeat.wwe.menu.batch;

import com.whatweeat.wwe.menu.batch.chunk.FileItemProcessor;
import com.whatweeat.wwe.menu.domain.Menu;
import com.whatweeat.wwe.menu.domain.MenuData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
public class MenuBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
                .<MenuData, Menu>chunk(2)
                .reader(fileItemReader())
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<MenuData> fileItemReader() {
        return new FlatFileItemReaderBuilder<MenuData>()
                .name("flatFile")
                .resource(new ClassPathResource("menu.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(MenuData.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("menuName", "nations", "excludes", "isSoup",
                        "isNoodle", "isRice", "usHealthy", "flavors",
                        "isInstant", "isAlcohol", "expense", "menuImage")
                .build();
    }

    @Bean
    public ItemProcessor<MenuData, Menu> fileItemProcessor() {
        return new FileItemProcessor();
    }
    @Bean
    public ItemWriter<Menu> fileItemWriter() {
        return new JpaItemWriterBuilder<Menu>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
