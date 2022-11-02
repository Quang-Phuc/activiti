package com.lptech.lp_projectdemo.repository;

import com.lptech.lp_projectdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
