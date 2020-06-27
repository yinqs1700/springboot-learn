package com.yinqs.cache.mapper;

import com.yinqs.cache.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    Department selectDept(Integer id);

}
