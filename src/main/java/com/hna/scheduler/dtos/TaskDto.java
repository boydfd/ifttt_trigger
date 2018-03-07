package com.hna.scheduler.dtos;

import com.hna.scheduler.entities.Task;
import com.hna.scheduler.enums.ProcessorType;
import com.hna.scheduler.utilities.EntityMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Trigger;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    Integer id;

    String triggerKey;

    @NotNull
    ProcessorType processor;

    Trigger.TriggerState status;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer intervalInSeconds;

    public Task toEntity() {
        return EntityMapper.INSTANCE.mapToEntity(this);
    }

    public static TaskDto from(Task task) {
        return EntityMapper.INSTANCE.mapToDto(task);
    }
}
