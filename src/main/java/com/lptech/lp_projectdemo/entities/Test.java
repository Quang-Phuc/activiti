package com.lptech.lp_projectdemo.entities;

import org.hibernate.annotations.Columns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test1")
public class Test {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

}
