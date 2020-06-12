package com.springboot.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yinqs
 */
@Controller
public class SampleController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }
}
