package com.zk.ssm_demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author panbing@supcon.com
 * @create 2017/9/21 10:02
 * @description
 */
@Controller("ssm_demo_profile_controller")   //这相当于controller的名字
@RequestMapping("/profile")
public class ProfileController {

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Value("${guitar_color}")
    private String guitarColor;

    @Value("${pick_color}")
    private String pickColor;

    @Value("${jdbc.username}")
    private String name;

    @Value("${nginx.imgPath}")
    private String path;

    @RequestMapping("/print.do")
    @ResponseBody
    //@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
    public void print() {
        logger.info("********************" + guitarColor + "********************");
        logger.info("********************" + pickColor + "********************");
        logger.info("********************" + name + "********************");
        logger.info("********************" + path + "********************");
    }

}
