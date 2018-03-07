package com.hna.scheduler.jobs;

import com.hna.scheduler.processors.Processor;
import com.hna.scheduler.processors.PulledData;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

@Slf4j
@DisallowConcurrentExecution
public class PullJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            Class<?> processorClass = Class.forName(
                    jobDataMap.getString("processorClass"));

            String url = jobDataMap.getString("url");

            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler()
                    .getContext()
                    .get("applicationContext");

            Processor processor = (Processor) applicationContext.getBean(processorClass);

            PulledData pulledData = processor.getPulledData(url);

            log.info("columns {}\n rows: {}",
                    pulledData.getColumnNames().size(),
                    pulledData.getRows().size());
        } catch (SchedulerException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}