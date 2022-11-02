package com.lptech.lp_projectdemo.repository;

import com.lptech.lp_projectdemo.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
}
