package com.zk.function.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Koori_Cc on 2017/9/7.
 * 跳转至索引页面
 */
@Controller("function_common_controller")
@RequestMapping("/function")
public class IndexController {

    //跳转到索引页
    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "function/index/index";
    }
}
