package com.zk.function.eCharts.controllers;

import com.zk.ssm_demo.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author panbing@supcon.com
 * @create 2017/11/28 15:31
 * @description
 */
@Controller("function_echarts_controller")
@RequestMapping("/function/eCharts")
public class EChartsController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toList.do")
    public String toList(ModelMap map) {
        return "/function/eCharts/list";
    }


    @RequestMapping(value="/showBar.do",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String showBar() {
        return userService.showBar();
    }

    @RequestMapping(value="/showPie.do",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String showPie() {
        return userService.showPie();
    }


}
