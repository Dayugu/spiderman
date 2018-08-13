package com.gzy.spider.spiderman.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {


    @RequestMapping("/test")
    public String test(){

        return "test";
    }


}
