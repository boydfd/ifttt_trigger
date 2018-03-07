package com.hna.scheduler.services;

import com.hna.scheduler.dtos.TaskDto;
import com.hna.scheduler.enums.ProcessorType;
import com.hna.scheduler.jobs.PullJob;
import com.hna.scheduler.repositories.TaskRepository;
import com.hna.scheduler.utilities.DateConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.quartz.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private Scheduler scheduler;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void shouldCreateQuartzTriggerWhenCreateTask() throws SchedulerException {
        int intervalInSeconds = 100;
        String url = "some url";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        TaskDto taskDto = prepareTaskDto(intervalInSeconds, url, startTime, endTime);

        given(scheduler.getTriggerState(any())).willReturn(Trigger.TriggerState.NORMAL);
        given(taskRepository.save(Mockito.eq(taskDto.toEntity()))).willReturn(taskDto.toEntity());

        TaskDto taskDtoReturned = taskService.create(taskDto);

        ArgumentCaptor<JobDetail> jobDetailCaptor = ArgumentCaptor.forClass(JobDetail.class);
        ArgumentCaptor<Trigger> triggerCaptor = ArgumentCaptor.forClass(Trigger.class);

        verify(scheduler).scheduleJob(jobDetailCaptor.capture(), triggerCaptor.capture());

        JobDetail jobDetail = jobDetailCaptor.getValue();
        Trigger trigger = triggerCaptor.getValue();

        assertAll(
                () -> assertEquals(trigger.getKey().toString(), taskDtoReturned.getTriggerKey()),
                () -> assertEquals(Trigger.TriggerState.NORMAL, taskDtoReturned.getStatus())
        );

        assertAll(
                () -> assertEquals(ProcessorType.NORMAL.getStringType(),
                        jobDetail.getJobDataMap().get("processorClass")),
                () -> assertEquals(PullJob.class, jobDetail.getJobClass())
        );

        assertAll(
                () -> assertEquals(startTime,
                        DateConverter.dateToLocalDateTime(trigger.getStartTime())),
                () -> assertEquals(endTime,
                        DateConverter.dateToLocalDateTime(trigger.getEndTime())),
                () -> assertEquals(intervalInSeconds * 1000L,
                        ((SimpleTrigger) trigger).getRepeatInterval()),
                () -> assertEquals(jobDetail.getKey(), trigger.getJobKey())
        );
    }



    private TaskDto prepareTaskDto(int intervalInSeconds, String url, LocalDateTime startTime, LocalDateTime endTime) {
        return TaskDto.builder()
                .intervalInSeconds(intervalInSeconds)
                .startTime(startTime)
                .endTime(endTime)
                .processor(ProcessorType.NORMAL)
                .build();
    }
}