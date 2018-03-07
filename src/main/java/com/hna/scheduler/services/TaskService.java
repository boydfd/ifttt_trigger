package com.hna.scheduler.services;

import com.hna.scheduler.dtos.TaskDto;
import com.hna.scheduler.entities.Task;
import com.hna.scheduler.jobs.PullJob;
import com.hna.scheduler.repositories.TaskRepository;
import com.hna.scheduler.utilities.DateConverter;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Scheduler scheduler;

    @Autowired
    public TaskService(TaskRepository taskRepository, Scheduler scheduler) {
        this.taskRepository = taskRepository;
        this.scheduler = scheduler;
    }

    @Transactional
    public TaskDto create(TaskDto taskDto) throws SchedulerException {
        Task task = taskDto.toEntity();
        Trigger trigger = createTrigger(task);
        task.setStatus(scheduler.getTriggerState(trigger.getKey()));
        task.setTriggerKey(trigger.getKey().toString());
        taskRepository.save(task);
        return TaskDto.from(task);
    }

    @NotNull
    private Trigger createTrigger(Task task) throws SchedulerException {
        JobDetail job = newJob(PullJob.class)
                .usingJobData("processorClass", task.getProcessor().getStringType())
                .build();

        Trigger trigger = newTrigger()
                .withSchedule(createScheduleBuilder(task.getIntervalInSeconds()))
                .endAt(DateConverter.localDateTime2Date(task.getEndTime()))
                .startAt(DateConverter.localDateTime2Date(task.getStartTime()))
                .forJob(job)
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return trigger;
    }

    private SimpleScheduleBuilder createScheduleBuilder(Integer intervalInSeconds) {
        SimpleScheduleBuilder simpleScheduleBuilder = simpleSchedule();
        if (intervalInSeconds != null) {
            simpleScheduleBuilder.withIntervalInSeconds(intervalInSeconds).repeatForever();
        }
        return simpleScheduleBuilder;
    }
}
