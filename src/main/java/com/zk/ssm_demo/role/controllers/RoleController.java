package com.zk.ssm_demo.role.controllers;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.role.services.RoleService;
import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import com.zk.ssm_demo.utils.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */

@RequestMapping("/role")
@Controller("ssm_demo_role_controller")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);


    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/toList.do")
    public String toList() {
        return "ssm_demo/role/list";
    }

    //--------------------------- 获取分页数据 start-----------------------------------------------
    /**
     * @param role
     * @param paginationVO
     * @return
     * @description 如果前台传过来多个对象,需要用xx.xx这种方式传值(驼峰),而且不能是json格式,需要用到@InitBinder 注解
     */
    @RequestMapping(value="/list.do",method= RequestMethod.POST)
    @ResponseBody
    public PaginationVO<Role> list(Role role ,PaginationVO<Role> paginationVO) {
        return roleService.paging(role,paginationVO);
    }

    @InitBinder("role")
    public void roleBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("role.");
    }

    @InitBinder("paginationVO")
    public void paginationVOBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("paginationVO.");
    }
    //------------------------------ 获取分页数据 end-----------------------------------------------

    @RequestMapping("/add.do")
    @ResponseBody
    public void add(@RequestBody Role role) {
        roleService.insertRole(role);
    }

    @RequestMapping("/edit.do")
    @ResponseBody
    public void edit(@RequestBody Role role) {
        roleService.updateRole(role);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public void delete(@RequestBody Role role) {
        String id = role.getId();
        roleService.deleteRoleById(id);
    }

    @RequestMapping("/toAssignRole.do")
    public String toAssignRole(String id,ModelMap model) {
        //根据用户的id查询出用户对象
        User user = userService.queryUserById(id);
        model.addAttribute(KeyUtils.USER,user);

        //查询相关联的角色
        List<Role> relList = roleService.queryRoleUserRelation(id);
        model.addAttribute(KeyUtils.REL_ROLE_LIST,relList);

        //所有的角色
        List<Role> roleList = roleService.queryRoleList();

        //取差集
        roleList.removeAll(relList);
        model.addAttribute(KeyUtils.NO_REL_ROLE_LIST,roleList);
        return "ssm_demo/role/assignRole";
    }


}
