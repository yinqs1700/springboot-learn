package com.yinqs.cache;

import com.yinqs.cache.entity.Employee;
import com.yinqs.cache.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void contextLoads() {

        System.out.println("查询到的员工"+employeeService.getEmp(1));
    }

    @Test
    public void testRedisTemplate(){
        Employee emp = employeeService.getEmp(1);
        redisTemplate.opsForValue().set("emp:2",emp);
    }

    @Test
    public void testEmployeeService(){
        System.out.println(employeeService.getEmp(1));
    }

    @Test
    public void testRedisGet(){
        ValueOperations<Object , Object> ops = redisTemplate.opsForValue();
        Employee employee = (Employee) ops.get("emp:2");
        System.out.println(employee);
    }
}
