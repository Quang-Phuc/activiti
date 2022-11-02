package com.lptech.lp_projectdemo.repository;

import com.lptech.lp_projectdemo.entities.RequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface RequestDetailRepository extends JpaRepository<RequestDetail, Long> {
}
