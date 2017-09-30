package com.zk.ssm_demo.role.controllers;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.role.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Koori_Cc on 2017/8/24.
 */

@RequestMapping("/role")
@Controller("ssm_demo_role_controller")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);


    @Autowired
    private RoleService roleService;

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



}
