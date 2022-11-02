package com.lptech.lp_projectdemo.mapper;

import com.lptech.lp_projectdemo.dto.activitiDto.TaskDto;
import org.activiti.engine.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ActivitiMapper {
    ActivitiMapper INSTANCE = Mappers.getMapper(ActivitiMapper.class);
    TaskDto toTaskDto(Task task);

    List<TaskDto> toTaskDtoList(List<Task> taskList);
}
