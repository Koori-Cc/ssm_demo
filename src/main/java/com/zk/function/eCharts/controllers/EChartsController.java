package com.zk.function.eCharts.controllers;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "/function/eCharts/list";
    }


}
