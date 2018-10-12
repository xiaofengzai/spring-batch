package com.enshub.uncompress;

import com.enshub.helloworld.WriteTasklet;
import com.enshub.mapper.PeopleMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.item.Chunk;
import org.springframework.batch.core.step.item.ChunkOrientedTasklet;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

/**
 * Created by szty on 2018/10/10.
 */
@Configuration
public class UnCompressConfiguration {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private PeopleMapper peopleMapper;
    private static String srcResouce="D:\\name.zip";
    private static String directory="D:\\name";
    private static String filename="name.txt";
    private static String finalResource=directory.concat("\\").concat(filename);

    @Autowired
    private StepBuilderFactory steps;
    @Autowired
    ApplicationContext applicationContext;
    @Bean
    DecompressTasklet decompressTasklet(){
        //todo
        return new DecompressTasklet(new FileSystemResource(srcResouce),directory,filename);
    }

    @Bean("fileItemReader")
    FlatFileItemReader reader(){
        FlatFileItemReader reader=new FlatFileItemReader();
        reader.setLineMapper(lineMapper());
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource(finalResource));
        return reader;
    }

    @Bean
    LineMapper lineMapper(){
        DefaultLineMapper lineMapper=new DefaultLineMapper();
        lineMapper.setLineTokenizer(delimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new ReadFileMapper());
        return lineMapper;
    }

    @Bean
    DelimitedLineTokenizer delimitedLineTokenizer(){
        return new DelimitedLineTokenizer("|");
    }

    @Bean
    ItemWriter writer(){
        ItemWriter writer=new FileWriter(applicationContext.getBean(DataSource.class),peopleMapper);
        return writer;
    }

    @Bean
    FileProcessor fileProcessor(){
        return new FileProcessor();
    }

    @Bean
    FileCleanTasklet clean(){
        FileCleanTasklet cleanTasklet=  new FileCleanTasklet();
        cleanTasklet.setResource(new FileSystemResource(srcResouce));
        return cleanTasklet;
    }

    @Bean("decompress")
    Step decompress(){
        return steps.get("decompress")
                .tasklet(decompressTasklet())
                .build();
    }
    @Bean("readWriter")
    @OnReadError
    Step readWriter( ){
        return steps.get("readWriter").chunk(1000).reader(reader()).writer(writer()).processor(fileProcessor()).build();
    }
    @Bean("cleanStep")
    Step cleanStep(){
        return steps.get("cleanStep")
                .tasklet(clean())
                .build();
    }

    @Bean("fileJob")
    public Job fileJob(){
        return jobs.get("fileJob")
                .listener(new JobExecListener())
                .incrementer(new RunIdIncrementer())
                .start(decompress())
                .next(readWriter())
                .next(cleanStep())
                .build();
    }
}
