package com.hna.scheduler.entities;

import com.hna.scheduler.enums.ProcessorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Trigger;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    Integer id;

    String triggerKey;

    @Enumerated(value = EnumType.STRING)
    ProcessorType processor;

    @Enumerated(value = EnumType.STRING)
    Trigger.TriggerState status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer intervalInSeconds;
}
