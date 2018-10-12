package com.enshub.helloworld;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by szty on 2018/10/9.
 */
public class WriteTasklet implements Tasklet {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.print(message);
        return RepeatStatus.FINISHED;
    }

    public WriteTasklet(String message) {
        this.message = message;
    }
}
