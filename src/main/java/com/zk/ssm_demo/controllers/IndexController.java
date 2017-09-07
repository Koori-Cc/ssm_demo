package com.zk.ssm_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Koori_Cc on 2017/9/7.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "index";
    }
}
