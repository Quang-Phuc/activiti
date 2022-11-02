package com.lptech.lp_projectdemo.repository;

import com.lptech.lp_projectdemo.entities.Request;
import com.lptech.lp_projectdemo.globalenum.RequestStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
    Optional<Request> findByIdAndStatus(Long id, RequestStatusEnum requestStatusEnum);
    Page<Request> findAllByStatusIsFalse(Pageable pageable);
    Optional<Request> findByName(String name);
}
