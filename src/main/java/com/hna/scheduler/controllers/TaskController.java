package com.hna.scheduler.controllers;

import com.hna.scheduler.dtos.TaskDto;
import com.hna.scheduler.services.TaskService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    TaskDto create(@RequestBody TaskDto taskDto) throws SchedulerException {
        return taskService.create(taskDto);
    }

}
