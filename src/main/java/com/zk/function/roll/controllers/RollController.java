package com.zk.function.roll.controllers;

import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/11 9:16
 * @description
 */
@Controller("function_roll_controller")
@RequestMapping("/function/roll")
public class RollController {

    private Logger logger = LoggerFactory.getLogger(RollController.class);

    @Autowired
    private UserService userService;

    @Value("${nginx_uri_photo}")
    private String photo_pre;




    @RequestMapping(value="/toList.do",method= RequestMethod.GET)
    public String toList(ModelMap modelMap) {
        List<User> userList = userService.queryUserList();
        if(userList.size() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(User u : userList) {
                if(u.getCreateTime() != null) {
                    u.setS_createTime(sdf.format(u.getCreateTime()));
                }
            }
        }
        modelMap.put("dataList",userList);
        modelMap.put("photo_pre",photo_pre);
        return "function/roll/list";
    }




}
