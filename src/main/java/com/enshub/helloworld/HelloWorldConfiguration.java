package com.enshub.helloworld;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by szty on 2018/10/9.
 */
@Configuration
@EnableBatchProcessing
public class HelloWorldConfiguration {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
//    @Bean
//    @Qualifier("hello")
//    WriteTasklet hello(){
//       return new WriteTasklet("hello");
//    }
//
//    @Bean
//    @Qualifier("world")
//    WriteTasklet world(){
//        return new WriteTasklet("world");
//    }

    @Bean("stepHello")
    public Step stepHello(){
        return steps.get("stepHello")
                .tasklet(new WriteTasklet("hello"))
                .build();
    }

    @Bean("stepWorld")
    public Step stepWorld(){
        return steps.get("stepWorld")
                .tasklet(new WriteTasklet("world"))
                .build();
    }

    @Bean("helloWorldJob")
    public Job helloWorldJob(){
        return jobs.get("helloWorldJob")
                .incrementer(new RunIdIncrementer())
                .start(stepHello())
                .next(stepWorld())
                .build();
    }
}
