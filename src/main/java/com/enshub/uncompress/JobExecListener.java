package com.enshub.uncompress;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * Created by szty on 2018/10/11.
 */
public  class JobExecListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("before start time: " + jobExecution.getStartTime().getTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("after end time: " + jobExecution.getEndTime().getTime());
    }
}
