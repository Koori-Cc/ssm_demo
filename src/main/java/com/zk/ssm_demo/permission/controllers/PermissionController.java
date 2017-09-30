package com.zk.ssm_demo.permission.controllers;


import com.zk.ssm_demo.common.entities.PaginationVO;
import com.zk.ssm_demo.permission.entities.Permission;
import com.zk.ssm_demo.permission.services.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Koori_Cc on 2017/8/24.
 */

@RequestMapping("/permission")
@Controller("ssm_demo_permission_controller")
public class PermissionController {

    private Logger logger = LoggerFactory.getLogger(PermissionController.class);


    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/toList.do")
    public String toList() {
        return "ssm_demo/permission/list";
    }

    //--------------------------- 获取分页数据 start-----------------------------------------------
    /**
     * @param permission
     * @param paginationVO
     * @return
     * @description 如果前台传过来多个对象,需要用xx.xx这种方式传值(驼峰),而且不能是json格式,需要用到@InitBinder 注解
     */
    @RequestMapping(value="/list.do",method= RequestMethod.POST)
    @ResponseBody
    public PaginationVO<Permission> list(Permission permission , PaginationVO<Permission> paginationVO) {
        return permissionService.paging(permission,paginationVO);
    }

    @InitBinder("permission")
    public void permissionBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("permission.");
    }

    @InitBinder("paginationVO")
    public void paginationVOBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("paginationVO.");
    }
    //------------------------------ 获取分页数据 end-----------------------------------------------

    @RequestMapping("/add.do")
    @ResponseBody
    public void add(@RequestBody Permission permission) {
        permissionService.insertPermission(permission);
    }

    @RequestMapping("/edit.do")
    @ResponseBody
    public void edit(@RequestBody Permission permission) {
        permissionService.updatePermission(permission);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public void delete(@RequestBody Permission permission) {
        String id = permission.getId();
        permissionService.deletePermissionById(id);
    }
}
