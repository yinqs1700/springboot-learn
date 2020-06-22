## SpringBoot整合Cache

>几个重要的缓存注解（概念）  

|概念 | 描述|  
|:---:|:----:|  
|Cache|缓存接口，定义缓存操作。实现有RedisCache，EhCache，ConccurentMapCache|
|CacheManager|缓存管理器，管理各种缓存组件|
|@Cacheable|针对方法配置，能够根据方法的请求参数对其结果进行缓存|
|@CacheEvict|清空缓存|
|@CachePut|保证方法被调用，又希望结果被缓存|
|@EnableCaching|开启基于注解的缓存|
|keyGenerator|缓存是key的生成策略|
|serialize|缓存数据时value序列化策略|

> 测试环境搭建

1.引入依赖
```xml
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
```
2.创建测试用数据库文件，创建数据库表对应的javabean封装数据库数据
3.整合Mybatis操作数据库，实现对应的简单CRUD操作
4.创建出相应的service，controller层

>Spring操作缓存


1.配置类上添加基于注解的缓存`@EnableCaching`

默认使用`SimpleCacheConfiguration`配置类中的`ConcurrentMapCacheManager`作为缓存管理器

2.在需要缓存的方法上加入相对于的缓存注解即可  
3.@Cacheable
```java
 /**
     * 讲方法运行的结果进行缓存，以后要是在缓存中有相同的键，则不需要在执行方法的内容，直接返回缓存
     *
     * CacheManager管理多个缓存组件，每一个缓存组件都有自己唯一的名字，包含以下属性：
     *      cacheNames/values：指定缓存组件的名字；
     *      key：缓存数据使用的key，可以用它来指定（默认是方法参数的值）
     *              spEL：#id，参数id的值 
     *      keyGenerator：key的生产器，可以指定key的生成器组件
     *      cacheManager:指定对应的缓存管理器，或者指定cacheResolver缓存解析器
     *      condition：指定符合条件的情况下才缓存
     *      unless: 否定缓存，当unless指定的条件为true，方法的返回值就不会被缓存
     *      sync:是否使用异步模式
     * @param id 传入的员工编号
     * @return  emp
     */
    @Cacheable(cacheNames = {"emp"},keyGenerator = "myKeyGenerator"/*key = "#root.methodName" + "[" + "#id"+"]"*/,condition = "#id>0",unless = "",sync = true)
    public Employee getEmp(Integer id) {
        log.info("获取到员工信息");
        return employeeMapper.getEmpById(id);
    }

```
先执行查缓存操作，如果没有缓存，再执行方法中的内容，并将方法中产生的需要缓存的对象缓存到Cache中

4.@CachePut
```java

    /**
     * 
     * @CachePut 调用方法，又更新缓存数据,参数与@Cacheable相似
     * @return  
     */
    @CachePut(cacheNames = {"emp"},key = "#result.id")
    public Employee updateEmp(Employee employee){
        employeeMapper.updateEmp(employee);
        return employee;
    }
    
```
先执行方法再存入缓存中，使用相同的key就代表更新缓存

5.@CacheEvict
```java

    /**
     * 
     * @CacheEvict : 缓存清除
     *      key: 指定清除的数据
     *      allEntries=true： 指定清除这个缓存中所有的数据
     * @return  
     */
    @CacheEvict(value = "emp",key = "#id")
    public void deleteEmp(Integer id){
        employeeMapper.delEmpById(id);
    }
```

默认代表缓存清除操作是在方法执行之后执行，如果出现异常缓存就不会清除,使用参数beforeInvocation=true代表清除缓存的操作时在方法运行之前执行，则即时方法出现异常，缓存都能够被清除。  

5.@Caching
```java

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
```

可以通过`@Caching`注解指定复杂的缓存规则

> SpringBoot整合Redis做为缓存

    