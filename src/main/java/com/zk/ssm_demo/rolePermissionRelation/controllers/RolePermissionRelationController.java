package com.zk.ssm_demo.rolePermissionRelation.controllers;


import com.zk.ssm_demo.permission.entities.Permission;
import com.zk.ssm_demo.permission.services.PermissionService;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.role.services.RoleService;
import com.zk.ssm_demo.rolePermissionRelation.entities.RolePermissionRelation;
import com.zk.ssm_demo.rolePermissionRelation.services.RolePermissionRelationService;
import com.zk.ssm_demo.utils.KeyUtils;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */

@RequestMapping("/rolePermissionRelation")
@Controller("ssm_demo_rolePermissionRelation_controller")
public class RolePermissionRelationController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;

    @RequestMapping("/toAssignPermission.do")
    public String toAssignPermission(String id,ModelMap model) {
        //根据角色的id查询出角色
        Role role = roleService.queryRoleById(id);
        model.addAttribute(KeyUtils.ROLE,role);

        //通过角色的id查询相关联的权限
        List<Permission> relList = permissionService.queryRolePermissionRelation(id);
        model.addAttribute(KeyUtils.REL_PERMISSION_LIST,relList);

        //所有的角色
        List<Permission> permissionList = permissionService.queryPermissionList();

        //取差集
        permissionList.removeAll(relList);
        model.addAttribute(KeyUtils.NO_REL_PERMISSION_LIST,permissionList);
        return "ssm_demo/permission/assignPermission";
    }

    @RequestMapping("/addAssignPermission.do")
    @ResponseBody
    public Integer addAssignPermission(String roleId,String permissionId) {
        try {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
            rolePermissionRelation.setId(UUIDUtils.getUUID());
            rolePermissionRelation.setRole_id(roleId);
            rolePermissionRelation.setPermission_id(permissionId);
            rolePermissionRelationService.insert(rolePermissionRelation);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping("/cancelAssignPermission.do")
    @ResponseBody
    public Integer cancelAssignPermission(String roleId,String permissionId) {
        try {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
            rolePermissionRelation.setRole_id(roleId);
            rolePermissionRelation.setPermission_id(permissionId);
            rolePermissionRelationService.delete(rolePermissionRelation);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
