package com.enshub.uncompress;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Created by szty on 2018/10/10.
 */
public class FileCleanTasklet implements Tasklet {
    private Resource resource;

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File file = resource.getFile();
        file.deleteOnExit();
        return RepeatStatus.FINISHED;
    }
}
