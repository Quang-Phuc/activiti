package com.lptech.lp_projectdemo.controller;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/activiti")
@RestController
public class DelegateServiceTaskController {
    private RuntimeService runtimeService;

    @Autowired
    public DelegateServiceTaskController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping("/service/delegate")
    public String startTheProcess(){
        runtimeService.startProcessInstanceByKey("java-delegate-service-task-process");
        return "đây là delegate service task";
    }
    @GetMapping("/service/expression")
    public String startTheProcess1(){
        runtimeService.startProcessInstanceByKey("method-expression-service-task-process");
        return "đây là expression service task";
    }

}
