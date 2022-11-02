package com.lptech.lp_projectdemo.service;

import com.lptech.lp_projectdemo.dto.activitiDto.ProcessResponseDto;
import com.lptech.lp_projectdemo.mapper.ActivitiMapper;
import com.lptech.lp_projectdemo.repository.RequestRepository;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActivitiService {
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final RequestRepository requestRepository;

    @Autowired
    public ActivitiService(RuntimeService runtimeService, TaskService taskService, RepositoryService repositoryService, RequestRepository requestRepository) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repositoryService = repositoryService;
        this.requestRepository = requestRepository;
    }

    public ProcessResponseDto startProcess(String processKey, Map<String, Object> initObject){
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(processKey)
                .transientVariables(initObject)
                .start();
        ProcessResponseDto processResponseDto = new ProcessResponseDto();
        processResponseDto.setProcessInstanceId(processInstance.getProcessInstanceId());
        processResponseDto.setTaskList(ActivitiMapper.INSTANCE.toTaskDtoList(getTasksByProcessInstanceId(processInstance.getProcessInstanceId())));
        return processResponseDto;
    }

    public String process(){
        TaskQuery taskList = taskService.createTaskQuery().orderByTaskCreateTime().asc();
        StringBuilder processInfo = new StringBuilder();
        processInfo.append("Số lượng processInstance: ").append(repositoryService.createProcessDefinitionQuery().count()).append("\n");
        taskList.list().forEach(task -> {
            processInfo.append(task.getId()).append(",").append(task.getName()).append(",").append(task.getAssignee()).append(",").append(task.getDescription()).append("\n");
        });
        return processInfo.toString();
    }

    public List<Task> getTasks(String name){
        return taskService.createTaskQuery().taskAssignee(name).list();
    }
    public List<Task> getTasks(){
        return taskService.createTaskQuery().list();
    }

    public List<Task> getTasksByProcessInstanceId(String processInstanceId){
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    public void completeTask(String taskId, Map<String, Object> map){
        if(map!=null){
            taskService.complete(taskId, map);
        }else {
            taskService.complete(taskId);
        }

    }

}
