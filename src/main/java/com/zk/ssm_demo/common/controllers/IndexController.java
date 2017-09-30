package com.zk.ssm_demo.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Koori_Cc on 2017/9/7.
 * 跳转至索引页面
 */
@Controller("ssm_demo_common_controller")
public class IndexController {

    //跳转到索引页
    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "ssm_demo/index/index";
    }
}
