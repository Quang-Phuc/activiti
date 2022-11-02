package com.lptech.lp_projectdemo.spectification;

import com.lptech.lp_projectdemo.entities.Request;
import com.lptech.lp_projectdemo.globalenum.RequestStatusEnum;
import com.lptech.lp_projectdemo.spectification.searchQueryCondition.RequestQueryCondition;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestSpecification implements Specification<Request> {

    RequestQueryCondition requestQueryCondition;

    public RequestSpecification(RequestQueryCondition requestQueryCondition) {
        this.requestQueryCondition = requestQueryCondition;
    }

    @Override
    public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        String name = requestQueryCondition.getName();
        String email = requestQueryCondition.getEmail();
        LocalDateTime endDate = requestQueryCondition.getEndDate();
        LocalDateTime startDate = requestQueryCondition.getStartDate();
        String[] test = requestQueryCondition.getTest();
        RequestStatusEnum status = null;
        if (requestQueryCondition.getStatus() != null) {
            status = RequestStatusEnum.valueOf(requestQueryCondition.getStatus());
        }
        if (name != null && name.trim().length() > 0) {
            predicateList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (email != null && email.trim().length() > 0) {
            predicateList.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
        }

        if(status!=null){
            predicateList.add(criteriaBuilder.equal(root.get("status"), status.ordinal()));
        }
        if(endDate!=null){
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate"),endDate));
        }
        if(startDate!=null){
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"),startDate));
        }

        List<Predicate> predicateListEmail = new ArrayList<>();
        if(test.length>0){
            for (int i =0 ;i< test.length;i++){
                if(test[i]!=null && test[i].trim().length()>0){
                    predicateListEmail.add(criteriaBuilder.like(root.get("email"),"%" + test[i] + "%"));
                }
            }
        }

        Predicate p = criteriaBuilder.or(predicateListEmail.toArray(new Predicate[0]));
        predicateList.add(p);
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
