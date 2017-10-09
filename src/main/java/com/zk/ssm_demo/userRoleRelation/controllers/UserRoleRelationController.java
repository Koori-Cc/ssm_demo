package com.zk.ssm_demo.userRoleRelation.controllers;


import com.zk.ssm_demo.userRoleRelation.entities.UserRoleRelation;
import com.zk.ssm_demo.userRoleRelation.services.UserRoleRelationService;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Koori_Cc on 2017/8/24.
 */

@RequestMapping("/userRoleRelation")
@Controller("ssm_demo_userRoleRelation_controller")
public class UserRoleRelationController {

    @Autowired
    private UserRoleRelationService userRoleRelationService;

    @RequestMapping("/addAssignRole.do")
    @ResponseBody
    public Integer addAssignRole(String userId,String roleId) {
        try {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setId(UUIDUtils.getUUID());
            userRoleRelation.setUser_id(userId);
            userRoleRelation.setRole_id(roleId);
            userRoleRelationService.insert(userRoleRelation);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    @RequestMapping("/cancelAssignRole.do")
    @ResponseBody
    public Integer cancelAssignRole(String userId,String roleId) {
        try {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUser_id(userId);
            userRoleRelation.setRole_id(roleId);
            userRoleRelationService.delete(userRoleRelation);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
