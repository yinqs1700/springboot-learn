package com.yinqs.cache.mapper;

import com.yinqs.cache.entity.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

    @Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId}")
    void  updateEmp(Employee employee);

    @Delete("delete from employee where id = #{id}")
    void delEmpById(Integer id);

    @Insert("insert into employee(lastName,email,gender,d_id) values (#{lastName},#{email},#{gender},#{dId})")
    void insertEmp(Employee employee);

    @Select("select * from employee where lastName = #{lastName}")
    Employee getEmpByLastName(String lastName);
}
