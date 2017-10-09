package com.zk.ssm_demo.rolePermissionRelation.services.impl;


import com.zk.ssm_demo.rolePermissionRelation.daos.RolePermissionRelationDao;
import com.zk.ssm_demo.rolePermissionRelation.entities.RolePermissionRelation;
import com.zk.ssm_demo.rolePermissionRelation.services.RolePermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
@Service("ssm_demo_rolePermissionRelation_service")
public class RolePermissionRelationServiceImpl implements RolePermissionRelationService {


    @Autowired
    private RolePermissionRelationDao rolePermissionRelationDao;

    public void insert(RolePermissionRelation rolePermissionRelation) {
        rolePermissionRelationDao.insert(rolePermissionRelation);
    }

    public void delete(RolePermissionRelation rolePermissionRelation) {
        rolePermissionRelationDao.delete(rolePermissionRelation);
    }


}
