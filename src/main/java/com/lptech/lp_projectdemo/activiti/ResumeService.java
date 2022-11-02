package com.lptech.lp_projectdemo.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResumeService  {

    public void storeResume() {
        System.out.println("Storing resume ...");
    }
    public void reject(DelegateExecution execution){
       Object variable =  execution.getVariable("telephoneInterviewOutcome");
        Object variable2 =  execution.getVariable("applicant");
        System.out.println("send mail");
        System.out.println("kết thúc process");
    }

}
