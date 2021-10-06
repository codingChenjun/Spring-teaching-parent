package com.dao.impl;

import com.dao.EmployeeDao;
import org.springframework.stereotype.Repository;
import javax.annotation.*;
/**
 * @author cj
 * @date 2019/11/4
 */
@Repository
public class EmployeDaoImpl implements EmployeeDao {
    @PostConstruct
    public void init(){
        System.out.println("EmployeeDaoImpl init---");
    }
    public EmployeDaoImpl() {

        System.out.println("EmployeDaoImpl的构造函数-----");
    }

    @Override
    public void update() {
        System.out.println("update in employeDaoImpl---");
    }
}
