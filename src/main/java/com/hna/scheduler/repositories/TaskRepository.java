package com.hna.scheduler.repositories;

import com.hna.scheduler.entities.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findByTriggerKey(String triggerKey);
}
