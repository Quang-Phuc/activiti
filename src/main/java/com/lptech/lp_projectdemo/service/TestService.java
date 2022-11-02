package com.lptech.lp_projectdemo.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void printSomethingInterested(){
        System.out.println("???????????????\n????????????????");
    }

    public void accept(DelegateExecution execution, Object condition){
        execution.setVariable("serviceVariable",condition);
        System.out.println("lưu vào db ngày nghhỉ");
        System.out.println("kết thúc process");
    }

    public void test2(DelegateExecution execution, Object var1){
        var1 = String.valueOf(Integer.parseInt((String) var1) +1);
        execution.setVariable("var1", var1);
        execution.setVariable("var2", var1);
        System.out.println("đang ở service, var1,var2 ="+var1);
    }


}
