package com.yinqs.cache.service;

import com.yinqs.cache.entity.Department;
import com.yinqs.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "dept")
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;


    @Autowired
    RedisCacheManager cacheManager;

    @Cacheable(key = "#root.args[0]")
    public Department getDeptById(Integer id){
        return departmentMapper.selectDept(id);
    }

    /**
     * 编码方式获取缓存
     * @param id
     */
    public Department getDeptById2(Integer id){
        Cache cache = cacheManager.getCache("dept");
        Cache.ValueWrapper wrapper = cache.get("1");
        if (wrapper == null){
            return departmentMapper.selectDept(id);
        }
        Department department = (Department) wrapper.get();

        return department;
    }

}
