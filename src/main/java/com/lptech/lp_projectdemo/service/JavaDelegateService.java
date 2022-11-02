package com.lptech.lp_projectdemo.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JavaDelegateService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Delegate service execute........");
    }
}
