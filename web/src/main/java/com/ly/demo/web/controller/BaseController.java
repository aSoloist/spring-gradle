package com.ly.demo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Soloist on 2018/3/18 23:41
 */
@RestController
public class BaseController {
    
    @RequestMapping("/index")
    public String getIndex() {
        return "index";
    }
}
