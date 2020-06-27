package com.yinqs.cache.controller;

import com.yinqs.cache.entity.Department;
import com.yinqs.cache.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dept")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("get/{id}")
    public Department getDeptById(@PathVariable("id") Integer id){
        return departmentService.getDeptById(id);
    }

    @GetMapping("get2/{id}")
    public Department getDeptById2(@PathVariable("id") Integer id){
        return departmentService.getDeptById2(id);
    }
}
