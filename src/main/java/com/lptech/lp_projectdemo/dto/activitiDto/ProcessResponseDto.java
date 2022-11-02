package com.lptech.lp_projectdemo.dto.activitiDto;


import java.util.List;

public class ProcessResponseDto {
    private String processInstanceId;
    private List<TaskDto> taskList;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public List<TaskDto> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskDto> taskList) {
        this.taskList = taskList;
    }
}
