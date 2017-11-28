package com.zk.function.roll.controllers;

import com.zk.ssm_demo.user.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author panbing@supcon.com
 * @create 2017/10/11 9:16
 * @description
 */
@Controller("function_roll_controller")
@RequestMapping("/function/roll")
public class RollController {

    private Logger logger = LoggerFactory.getLogger(RollController.class);


    @RequestMapping(value="/toList.do",method= RequestMethod.GET)
    public String toList(ModelMap modelMap) {
        User u = new User();
        u.setName("koori");
        u.setAge(24);
        modelMap.put("user",u);
        return "function/roll/list";
    }


}
