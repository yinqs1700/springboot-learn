package com.yinqs.cache.controller;

import com.yinqs.cache.entity.Employee;
import com.yinqs.cache.service.EmployeeService;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("emp")
public class EmployeeController {



    @Autowired
    EmployeeService employeeService;

    @GetMapping("{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        RedissonClient redissonClient = Redisson.create();
        RLock myLock = redissonClient.getLock("myLock");
        myLock.lock(10, TimeUnit.SECONDS);
        try {
            return employeeService.getEmp(id);
        }catch (Exception e){
        }finally {
            myLock.unlock();
        }
        return null;
    }


    @PostMapping("edit")
    public Map<String, Object> updateEmp(@RequestBody Employee employee) {
        Employee emp = employeeService.updateEmp(employee);

        Map<String, Object> map = new HashMap<>();

        map.put("更新成功", emp);

        return map;
    }

    @PostMapping("add")
    public String addEmp(@RequestBody Employee employee){
        employeeService.add(employee);
        return "success";
    }

    @DeleteMapping("{id}")
    public  Map<String,Object> deleteEmp(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success",id);
        return map;
    }

    @GetMapping("name")
    public Map<String, Object> getEmpByLastName(String lastName) {
        Map<String,Object> map = new HashMap<>();

        Employee employee = employeeService.getEmployee(lastName);
        map.put("success","查询成功");
        map.put("employee",employee);
        return map;
    }

    @DeleteMapping("delete")
    public Map<String ,String > deleteAllCaches(){
        employeeService.deleteAllCache();
        Map<String,String> map = new HashMap<>();
        map.put("success","删除所有Cache成功");
        return map;
    }


}
