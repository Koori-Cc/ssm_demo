package com.zk.function.eCharts.controllers;

import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        List<User> userList = userService.queryUserList();




        map.put("userList",userList);
        return "/function/eCharts/list";
    }




}
