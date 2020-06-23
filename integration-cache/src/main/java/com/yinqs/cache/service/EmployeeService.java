package com.yinqs.cache.service;


import com.yinqs.cache.entity.Employee;
import com.yinqs.cache.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


    /**
     * 讲方法运行的结果进行缓存，以后要是在缓存中有相同的键，则不需要在执行方法的内容，直接返回缓存
     * <p>
     * CacheManager管理多个缓存组件，每一个缓存组件都有自己唯一的名字，包含以下属性：
     * cacheNames/values：指定缓存组件的名字；
     * key：缓存数据使用的key，可以用它来指定（默认是方法参数的值）
     * spEL：#id，参数id的值
     * keyGenerator：key的生产器，可以指定key的生成器组件
     * cacheManager:指定对应的缓存管理器，或者指定cacheResolver缓存解析器
     * condition：指定符合条件的情况下才缓存
     * unless: 否定缓存，当unless指定的条件为true，方法的返回值就不会被缓存
     * sync:是否使用异步模式
     *
     * @param id 传入的员工编号
     * @return emp
     */
    @Cacheable(cacheNames = {"emp"},/*keyGenerator = "myKeyGenerator"*/key = "#root.args[0]", condition = "#id>0", sync = true)
    public Employee getEmp(Integer id) {
        log.info("---------------获取到员工信息-------------");
        return employeeMapper.getEmpById(id);
    }

    /**
     * @return
     * @CachePut 调用方法，又更新缓存数据,参数与@Cacheable相似
     */
    @CachePut(cacheNames = {"emp"}, key = "#result.id")
    public Employee updateEmp(Employee employee) {
        employeeMapper.updateEmp(employee);
        return employee;
    }


    /**
     * @return
     * @CacheEvict : 缓存清除
     * key: 指定清除的数据
     * allEntries=true： 指定清除这个缓存中所有的数据
     * 默认代表缓存清除操作是在方法执行之后执行，如果出现异常缓存就不会清除
     * beforeInvocation=true
     * 代表清除缓存的操作时在方法运行之前执行，则即时方法出现异常，缓存都能够被清除
     */
    @CacheEvict(value = "emp", key = "#id")
    public void deleteEmp(Integer id) {
        employeeMapper.delEmpById(id);
    }

    @CacheEvict(value = "emp")
    public void deleteAllCache(){
        // do nothing
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = "emp", key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp", key = "#result.lastName"),
                    @CachePut(value = "emp", key = "#result.email")
            },
            evict = {

            }
    )
    public Employee getEmployee(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }

    public void add(Employee employee) {
        employeeMapper.insertEmp(employee);
    }


}
