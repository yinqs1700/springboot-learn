package com.yinqs.cache.controller;

import com.yinqs.cache.entity.Employee;
import com.yinqs.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("emp")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {

        return employeeService.getEmp(id);
    }

    @PostMapping("edit")
    public Map<String, Object> updateEmp(@RequestBody Employee employee) {
        Employee emp = employeeService.updateEmp(employee);

        Map<String, Object> map = new HashMap<>();

        map.put("更新成功", emp);

        return map;
    }

    @DeleteMapping("{id}")
    public  Map<String,Object> deleteEmp(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",id);
        return map;
    }

    @GetMapping("{lastName}")
    public Map<String, Object> getEmpByLastName(@PathVariable("lastName") String lastName) {
        Map<String,Object> map = new HashMap<>();

        Employee employee = employeeService.getEmployee(lastName);
        map.put("success","查询成功");
        map.put("employee",employee);
        return map;
    }


}
