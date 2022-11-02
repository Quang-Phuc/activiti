package com.lptech.lp_projectdemo.controller;

import com.lptech.lp_projectdemo.dto.activitiDto.Applicant;
import com.lptech.lp_projectdemo.dto.activitiDto.ProcessResponseDto;
import com.lptech.lp_projectdemo.service.ActivitiService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-task")
public class ActivitiController {
    private final ActivitiService userTaskService;
    @Autowired
    private TaskService taskService;

    @Autowired
    public ActivitiController(ActivitiService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @PostMapping("/process")
    public ProcessResponseDto startProcess(@RequestParam String processKey, @RequestBody Map<String, Object> initObject){


        Applicant applicant = new Applicant("John Doe", "john@activiti.org", "12344");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applicant", applicant);
        ProcessResponseDto processInstance = userTaskService.startProcess(processKey, variables);

        userApi( processInstance.getProcessInstanceId(),false);
        Map<String, Object> taskVariables;

        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .orderByTaskName().asc()
                .list();
//        Assert.assertEquals(2, tasks.size());
//        Assert.assertEquals("Financial negotiation", tasks.get(0).getName());
//        Assert.assertEquals("Tech interview", tasks.get(1).getName());

        // Completing both should wrap up the subprocess, send out the 'welcome mail' and end the process instance
        taskVariables = new HashMap<String, Object>();
        taskVariables.put("techOk", true);
        taskService.complete(tasks.get(0).getId(), taskVariables);

        taskVariables = new HashMap<String, Object>();
        taskVariables.put("financialOk", true);
        taskService.complete(tasks.get(1).getId(), taskVariables);

        // Verify email
//        Assert.assertEquals(1, wiser.getMessages().size());

        // Verify process completed
//        Assert.assertEquals(1, historyService.createHistoricProcessInstanceQuery().finished().count());
        return null;
    }

    private void userApi(String processInstanceId,boolean telephoneInterviewOutcome) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskCandidateGroup("dev-managers")
                .singleResult();

        Map<String, Object> taskVariables = new HashMap<String, Object>();
        Applicant applicant = new Applicant("John Doe", "john@activiti.org", "12344");
        taskVariables.put("applicant", applicant);
        taskVariables.put("telephoneInterviewOutcome", telephoneInterviewOutcome);
        taskService.complete(task.getId(), taskVariables);
    }

    @GetMapping("/tasks")
    public String getTasks(@RequestParam String assignee) {
        if (assignee != null && assignee.trim().length() > 0) {
            List<Task> taskList = userTaskService.getTasks(assignee);
            return taskList.toString();
        } else {
            List<Task> taskList = userTaskService.getTasks();
            return taskList.toString();
        }


    }
    @GetMapping("/tasks/{processInstanceId}")
    public String getTasksByProcessInstanceId(@PathVariable String processInstanceId) {
        return userTaskService.getTasksByProcessInstanceId(processInstanceId).toString();


    }

    @GetMapping("/complete")
    public String completeTask(@RequestParam String taskId, @RequestBody Map<String, Object> map) {
        userTaskService.completeTask(taskId, map);
        return taskId + " has been completed.";
    }

    @GetMapping
    public Map<String, Object> test() {
        Map<String, Object> temp = new HashMap<>();
        temp.put("condition", 1);
        return temp;
    }
}
