package com.zk.ssm_demo.controllers;

import com.zk.ssm_demo.entities.PaginationVO;
import com.zk.ssm_demo.entities.User;
import com.zk.ssm_demo.services.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author panbing@supcon.com
 * @create 2017/9/13 9:19
 * @description
 */
@Controller
@RequestMapping("/kendo")
public class KendoController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(KendoController.class);

    @ModelAttribute("prepareUser")
    private User prepareUser() {
        return new User();
    }


    @RequestMapping("/toList.do")
    public String toList() {
        return "kendo/list";
    }

    @RequestMapping(value="/list.do",method = RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestBody PaginationVO vo) {     //这里加上 @RequestBody 才能接收参数,转换json格式
        Long page = vo.getPage();
        vo.setPageNo(page);
        vo = userService.paging(vo);

        return vo;
    }

    @RequestMapping(value="/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public void delete(@RequestBody User user) {   //前台传过来的是json格式的字符串!!
        String id = user.getId();
        Integer result = userService.deleteUserById(id);
    }

    @RequestMapping(value="/edit.do",method = RequestMethod.POST)
    @ResponseBody
    public void edit(@RequestBody User user) {
        Integer result = userService.updateUser(user);
    }

    @RequestMapping(value="/add.do",method = RequestMethod.POST)
    @ResponseBody
    public void add(@RequestBody User user) {          //返回值是void,也必须加 @ResponseBody ,不然会找不到路径
        userService.register(user);
    }


    @RequestMapping(value="/detail.do",method = RequestMethod.POST)
    @ResponseBody
    public Object detail(@RequestBody User user) {
        User u = userService.queryUserById(user.getId());
        return u;
    }

    @RequestMapping(value="/address.do",method = RequestMethod.POST)
    @ResponseBody
    public Object address() {
        List<User> list = userService.queryUserList();
        return list;
    }

    @RequestMapping(value="/addressList.do",method = RequestMethod.POST)
    @ResponseBody
    public Object addressList() {
        List<String> list = userService.queryAddress();
        return list;
    }

}
