package com.lptech.lp_projectdemo.service;

import com.lptech.lp_projectdemo.entities.Order;
import com.lptech.lp_projectdemo.entities.User;
import com.lptech.lp_projectdemo.globalenum.OrderStatus;
import com.lptech.lp_projectdemo.repository.OrderRepository;
import com.lptech.lp_projectdemo.repository.UserRepository;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ChickenKillingProcessService {
    private final String KEY = "toidibanga";
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Autowired
    public ChickenKillingProcessService(UserRepository userRepository, OrderRepository orderRepository, RuntimeService runtimeService, TaskService taskService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }


    public String process(Map<String, Object> map){
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(KEY)
                .transientVariables(map)
                .start();
        return processInstance.getProcessInstanceId();
    }

    public void completeTask(String taskId, Map<String, Object> map){
        if(map!=null){
            taskService.complete(taskId, map);
        }else {
            taskService.complete(taskId);
        }
    }

    public boolean informationProcessing(DelegateExecution execution){
        if(1==1){
            execution.setVariable("accepted","1");
            Order order = createOrder(execution.getVariables());
            User user = order.getUser();
            execution.setVariable("orderId", order.getId());
            execution.setVariable("userId", user.getId());
        }else {
            execution.setVariable("accepted","0");
        }
        return false;
    }
    public Order createOrder(Map<String, Object> map){
        Optional<User> u = userRepository.findById(2L);
        if(u.isEmpty()) throw new RuntimeException("User not found!");
        Order order = new Order();
        order.setQuantity((Integer) map.get("quantity"));
        order.setStatus(OrderStatus.PROCESSING);
        order.setUser(u.get());
        orderRepository.save(order);
        return order;
    }

    public void cancelOrder(Object orderId){
        Optional<Order> o = orderRepository.findById((Long) orderId);
        if (o.isEmpty()) throw new RuntimeException("Order not found!");
        o.get().setStatus(OrderStatus.CANCElED);
    }

    public void refund(Object userId, DelegateExecution execution){
        System.out.println(".....Refund for " + userId +" .....");
        execution.setVariable("refundStatus","1");
    }

    public void orderEnding(Object orderId){
        Optional<Order> o = orderRepository.findById((Long) orderId);
        if (o.isEmpty()) throw new RuntimeException("Order not found!");
        o.get().setStatus(OrderStatus.CLOSE);
    }

    public void payment(DelegateExecution execution){
        System.out.println(".....xu ly.....");
        execution.setVariable("urlPayment", "urlabcxyz");
    }

    public Object getVariable(String processInstanceId, String variableName){
        ExecutionEntity executionEntity = (ExecutionEntity) runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        if(executionEntity == null) return null;
        return executionEntity.getVariable(variableName);

    }
}
