package com.zk.ssm_demo.userRoleRelation.services.impl;


import com.zk.ssm_demo.userRoleRelation.daos.UserRoleRelationDao;
import com.zk.ssm_demo.userRoleRelation.entities.UserRoleRelation;
import com.zk.ssm_demo.userRoleRelation.services.UserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
@Service("ssm_demo_userRoleRelation_service")
public class UserRoleRelationServiceImpl implements UserRoleRelationService {

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;


    public void insert(UserRoleRelation userRoleRelation) {
        userRoleRelationDao.insert(userRoleRelation);
    }

    public void delete(UserRoleRelation userRoleRelation) {
        userRoleRelationDao.delete(userRoleRelation);
    }

}
