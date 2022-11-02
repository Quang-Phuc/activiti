package com.lptech.lp_projectdemo.controller;

import com.lptech.lp_projectdemo.controller.Response.MessageResponse;
import com.lptech.lp_projectdemo.dto.activitiDto.TaskDto;
import com.lptech.lp_projectdemo.mapper.ActivitiMapper;
import com.lptech.lp_projectdemo.service.ActivitiService;
import com.lptech.lp_projectdemo.service.ChickenKillingProcessService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ck")
public class ChickenKillingController {
    private final ChickenKillingProcessService chickenKillingProcessService;
    private final ActivitiService activitiService;

    @Autowired
    public ChickenKillingController(ChickenKillingProcessService chickenKillingProcessService, ActivitiService activitiService) {
        this.chickenKillingProcessService = chickenKillingProcessService;
        this.activitiService = activitiService;
    }

    @PostMapping("/completeTask")
    public void completeTask(@RequestParam String taskId, @RequestBody Map<String, Object> map){
        activitiService.completeTask(taskId,map);
    }

    @PostMapping("/processing")
    public MessageResponse informationProcessing(@RequestBody Map<String, Object> information){
        String message = chickenKillingProcessService.process(information);
        return new MessageResponse(message);
    }

    @GetMapping("/payment")
    public MessageResponse payment(@RequestParam String processInstanceId){
        Object urlPayment = chickenKillingProcessService.getVariable(processInstanceId,"urlPayment");
        return new MessageResponse((String) urlPayment);
    }

    @PostMapping("/payment/complete")
    public void paymentPost(@RequestParam String taskId){
        chickenKillingProcessService.completeTask(taskId,null);
    }

    @GetMapping("/tasks")
    public List<TaskDto> getTaskList(String processInstanceId){
        return ActivitiMapper.INSTANCE.toTaskDtoList(activitiService.getTasksByProcessInstanceId(processInstanceId));
    }
}