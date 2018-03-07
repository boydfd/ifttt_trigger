package com.hna.scheduler.utilities;

import com.hna.scheduler.dtos.TaskDto;
import com.hna.scheduler.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    Task mapToEntity(TaskDto object);


    TaskDto mapToDto(Task object);
}